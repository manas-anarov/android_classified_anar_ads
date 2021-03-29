package com.example.anarads.edit

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anarads.api.APIService
import com.example.anarads.api.ApiUtils
import com.example.anarads.utils.GetSetData

import com.example.anarads.R
import com.example.anarads.model.City
import com.example.anarads.model.Token
import com.example.anarads.uploader.UploadAdapter

import com.example.anarads.uploader.getFileName
import com.example.anarads.uploader.snackbar
import kotlinx.android.synthetic.main.create_post.*
import kotlinx.android.synthetic.main.create_post.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class EditPost : Fragment(){

    lateinit var context: AppCompatActivity

    private lateinit var image_view_s: ImageView
    private lateinit var image_list_s: RecyclerView

    private var selectedImageUri: Uri? = null
    var ImagesURIList: ArrayList<Uri?> = ArrayList()
    var upload_adapter = UploadAdapter(arrayListOf())


    private lateinit var btn_create: Button

    var area: Int = 1
    var group: Int = 1
    private lateinit var ed_title: EditText
    private lateinit var ed_description: EditText
    private lateinit var ed_price: EditText
    var item_type: Int = 1

    private lateinit var adapter: ArrayAdapter<City>

    val TAG = "FragmentTwo"

    override fun onAttach(context: Context) {
        Log.d(ContentValues.TAG,"onAttach")
        super.onAttach(context)
        this.context = context as AppCompatActivity
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.edit_post,
                container, false)


        btn_create = view.button_my
        ed_title = view.ed_title
        ed_description = view.ed_description
        ed_price = view.ed_price


        image_view_s = view.image_view
        image_list_s = view.image_list

        image_view_s.setOnClickListener {
            openImageChooser()
        }

        // Creates a vertical Layout Manager
        image_list_s.layoutManager = LinearLayoutManager(context)

        // Access the RecyclerView Adapter and load the data into it
        upload_adapter = UploadAdapter(ImagesURIList)
        image_list_s.adapter = upload_adapter


        val spinner = view.findViewById<Spinner>(R.id.spinner_city)
        val customObjects = getCityes()
        adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, customObjects)

        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // You can define you actions as you want
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

                val selectedObject = spinner.selectedItem as City

                area = selectedObject.id.toInt()
            }
        }




        btn_create.setOnClickListener {
            serverSend()
        }


        return view
    }

    private fun getCityes(): ArrayList<City> {
        val customObjects = ArrayList<City>()
        customObjects.apply {
            add(City(1, "Chuy"))
            add(City(2, "Osh"))
        }
        return customObjects
    }



    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE -> {
                    selectedImageUri = data?.data
//                    image_view.setImageURI(selectedImageUri)

                    selectedImageUri?.let { ImagesURIList.add(it) }
                    image_list.adapter = UploadAdapter(ImagesURIList)
                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun multipart_format(fileUri: Uri?): MultipartBody.Part {

        val file = File(context.cacheDir, fileUri?.let { requireActivity().contentResolver.getFileName(it) })

        val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
        return MultipartBody.Part.createFormData("files", file.getName(), requestBody)

    }



    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun serverSend() {



        if (selectedImageUri == null) {
            layout_root.snackbar("Select an Image First")
            return
        }
//        ContentResolver contentResolver = getAc
        val parcelFileDescriptor =
                requireActivity().contentResolver.openFileDescriptor(selectedImageUri!!, "r", null) ?: return
        val image_lists: ArrayList<MultipartBody.Part> = ArrayList()


        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(context.cacheDir, requireActivity().contentResolver.getFileName(selectedImageUri!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)



        val items_updated = upload_adapter.getAllItems()

        for(item in items_updated ){
            image_lists.add(multipart_format(item))
        }




        val token_ex_ap: String = GetSetData.getMySavedToken(requireContext().applicationContext)

        var mAPIService: APIService? = null

        mAPIService = ApiUtils.apiService

        mAPIService!!.createPostUni(
                token_ex_ap,
                area,
                group,
                ed_title.text.toString(),
                ed_description.text.toString(),
                ed_price.text.toString().toInt(),
                true,
                item_type,
                image_lists
        ).enqueue(object : Callback<Token> {

            override fun onResponse(call: Call<Token>, response: Response<Token>) {

                if (response.isSuccessful()) {

                    val getted_token = response.body()!!.token
                    val preferences = activity!!.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                    val prefLoginEdit = preferences.edit()
                    prefLoginEdit.putString("token", getted_token)
                    prefLoginEdit.commit()

                }
            }

            override fun onFailure(call: Call<Token>, t: Throwable) {

            }
        })
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        Log.d(TAG, "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "onResume")
        super.onResume()


    }

    override fun onPause() {
        Log.d(TAG, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach")
        super.onDetach()
    }

    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 101
    }
}