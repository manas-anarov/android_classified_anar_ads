package com.example.anarads.category

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anarads.R
import com.example.anarads.api.PostService
import com.example.anarads.data.PostRepository
import com.example.anarads.databinding.CategoryFragmentBinding
import com.example.anarads.model.PostResult

import kotlinx.android.synthetic.main.layout_dialog.view.*



class CategoryFragment : Fragment(){


    private lateinit var binding: CategoryFragmentBinding
    private val adapter = PostAdapter()


    val brand_types = arrayOf("Avto", "Login", "BMW")
    var car_type: Int = 1

    val post_types = arrayOf("Vse", "Avto", "Nedviga")
    var post_type: Int = 1

    val city_types = arrayOf("Chuy", "Osh", "Djalal-Abad")
    var city_type: Int = 1



    lateinit var rootView:View;

    private fun providePostRepository(): PostRepository {
        return PostRepository(PostService.create())
    }

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = "ss"

    }

    private lateinit var viewModel: CategoryFragmentViewModel

    lateinit var context: AppCompatActivity
    var filter: HashMap<String,String> = HashMap<String,String>()




    val TAG = "CreateContainer"

    override fun onAttach(context: Context) {
        Log.d(TAG,"onAttach")
        super.onAttach(context)
        this.context = context as AppCompatActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG,"onCreate")
        super.onCreate(savedInstanceState)
    }



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.category_fragment, container, false)
        return rootView


    }






    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        binding = CategoryFragmentBinding.bind(rootView)


        viewModel = ViewModelProvider(this, ViewModelCategoryFragmentFactory(providePostRepository()))
                .get(CategoryFragmentViewModel::class.java)



        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(decoration)
        setupScrollListener()

//        filter.put("sa", "as")


        val btn = rootView.findViewById<View>(R.id.sort_btn) as Button
        btn.setOnClickListener{
            showDialog()
        }

        val btn_sort = rootView.findViewById<View>(R.id.search_btn) as Button
        btn_sort.setOnClickListener{

            initAdapterNew()


//            updateRepoListFromInput()
            initSearch()
        }

    }


    private fun showDialog() {

        val mDialogView = LayoutInflater.from(context).inflate(R.layout.layout_dialog, null, false)
        val mBuilder = AlertDialog.Builder(context)
                .setView(mDialogView)
                .setTitle("Filtr")


        val  mAlertDialog = mBuilder.show()




            val spinner_brand = mDialogView.findViewById<Spinner>(R.id.spinner_dialog_category_type)
            spinner_brand?.adapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, brand_types) as SpinnerAdapter
            spinner_brand?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    println("erreur")
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val type = parent?.getItemAtPosition(position).toString()
                    car_type = position

                    val brand_dialog :Int = position + 1
                    filter.put("brand", brand_dialog.toString())




                }
            }

            val spinner_post_type = mDialogView.findViewById<Spinner>(R.id.spinner_dialog_post_type)
            spinner_post_type?.adapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, post_types) as SpinnerAdapter
            spinner_post_type?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    println("erreur")
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val type = parent?.getItemAtPosition(position).toString()

                    post_type = position

                    val post_type_dialog :Int = position + 1
                    filter.put("post_type", post_type_dialog.toString())


                }
            }

            val spinner_city = mDialogView.findViewById<Spinner>(R.id.spinner_dialog_city)
            spinner_city?.adapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, city_types) as SpinnerAdapter
            spinner_city?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    println("erreur")
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val type = parent?.getItemAtPosition(position).toString()




                    city_type = position
//                    Toast.makeText(context,type, Toast.LENGTH_LONG).show()
//                    Toast.makeText(context,city_type.toString(), Toast.LENGTH_LONG).show()

                    val city_type_dialog :Int = position + 1
                    filter.put("area", city_type_dialog.toString())


                }
            }


            mDialogView.dialogLoginBtn.setOnClickListener {
                //dismiss dialog
                mAlertDialog.dismiss()

            }
            //cancel button click of custom layout
            mDialogView.dialogCancelBtn.setOnClickListener {
                //dismiss dialog
                mAlertDialog.dismiss()
            }

            mDialogView.dialogClearBtn.setOnClickListener {
                //dismiss dialog
                filter.values.clear()
            }







    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, binding.searchRepo.text.trim().toString())
    }






    private fun initAdapterNew() {
        binding.list.adapter = adapter
        viewModel.repoResult.observe(context) { result ->
            when (result) {
                is PostResult.Success -> {
                    showEmptyList(result.data.isEmpty())
                    adapter.submitList(result.data)
                }
                is PostResult.Error -> {
                    Toast.makeText(
                            context,
                            "\uD83D\uDE28 Wooops $result.message}",
                            Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }



    private fun updateRepoListFromInput() {
        binding.searchRepo.text.trim().let {
            if (it.isNotEmpty()) {
                binding.list.scrollToPosition(0)
                filter.put("search", it.toString())
                viewModel.searchRepo(filter)
            }
        }

    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.emptyList.visibility = View.VISIBLE
            binding.list.visibility = View.GONE
        } else {
            binding.emptyList.visibility = View.GONE
            binding.list.visibility = View.VISIBLE
        }
    }

    private fun setupScrollListener() {
        val layoutManager = binding.list.layoutManager as LinearLayoutManager
        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }

    private fun initSearch() {
        updateRepoListFromInput()
    }






}