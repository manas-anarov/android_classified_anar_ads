package com.example.anarads.comments
import android.content.ContentValues
import android.content.Context
import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import com.example.anarads.api.APIService
import com.example.anarads.api.ApiUtils
import com.example.anarads.utils.GetSetData


import com.example.anarads.R
import com.example.anarads.model.Token
import kotlinx.android.synthetic.main.comment_create.*
import kotlinx.android.synthetic.main.comment_create.view.*
import kotlinx.android.synthetic.main.create_post.view.*
import kotlinx.android.synthetic.main.create_post.view.button_my


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM2 = "param2"

class CreateComment : Fragment(){

    private var post_id_my = 1
    private var param2: Int? = null

    lateinit var context: AppCompatActivity

    val TAG = "FragmentCreateComment"
    private lateinit var btn_save_comment: Button

    companion object {

        @JvmStatic
        fun newInstance(param2: Int) =
                CreateComment().apply {
                arguments = Bundle().apply {
                putInt(com.example.anarads.comments.ARG_PARAM2, param2)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param2 = it.getInt(com.example.anarads.comments.ARG_PARAM2)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.comment_create,
                container, false)

        btn_save_comment = view.comment_create
        btn_save_comment.setOnClickListener {
            serverSend()
        }


        return view
    }









    fun serverSend() {



        val token_ex_ap: String = GetSetData.getMySavedToken(requireContext().applicationContext)


        var mAPIService: APIService? = null

        mAPIService = ApiUtils.apiService


        mAPIService!!.createComment(
                token_ex_ap,
                param2,
                comment_ed.text.toString(),
        ).enqueue(object : Callback<Token> {

            override fun onResponse(call: Call<Token>, response: Response<Token>) {

                if (response.isSuccessful()) {


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

    override fun onAttach(context: Context) {
        Log.d(ContentValues.TAG,"onAttach")
        super.onAttach(context)
        this.context = context as AppCompatActivity
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach")
        super.onDetach()
    }


}