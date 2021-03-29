package com.example.anarads.comments
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anarads.Fragment1
import com.example.anarads.R
import com.example.anarads.api.PostService
import com.example.anarads.data.CommentRepository
import com.example.anarads.databinding.CommentFragmentBinding
import com.example.anarads.model.CommentResult


private const val ARG_PARAM1 = "param1"


class CommentsFragment : Fragment() {


    // TODO: Rename and change types of parameters
    private var param1: Int? = null
//    private var param2: String? = null


    lateinit var context: AppCompatActivity
    private lateinit var binding: CommentFragmentBinding
    private val adapter = CommentsAdapter()
    lateinit var rootView:View;

    private fun provideGithubRepository(): CommentRepository {
        return CommentRepository(PostService.create())
    }

    companion object {
//        fun newInstance() = CommentsFragment()
        private const val LAST_SEARCH_QUERY: String = "last_search_query"

        @JvmStatic
        fun newInstance(param1: Int) =
                CommentsFragment().apply {
                    arguments = Bundle().apply {
                        putInt(com.example.anarads.comments.ARG_PARAM1, param1)
                    }
                }

    }

    private lateinit var viewModel: CommentsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(com.example.anarads.comments.ARG_PARAM1)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.comment_fragment, container, false)





        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        binding = CommentFragmentBinding.bind(rootView)


        viewModel = ViewModelProvider(this, CommentViewModelFactory(provideGithubRepository()))
                .get(CommentsViewModel::class.java)



        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(decoration)
        setupScrollListener()

        initAdapter()
        if (viewModel.repoResult.value == null) {
            viewModel.setPostId(param1)
            viewModel.searchRepo()
        }
        initSearch()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, "1")
    }

    private fun initAdapter() {
        binding.list.adapter = adapter
        viewModel.repoResult.observe(context) { result ->
            when (result) {
                is CommentResult.Success -> {
                    showEmptyList(result.data.isEmpty())
                    adapter.submitList(result.data)
                }
                is CommentResult.Error -> {
                    Toast.makeText(
                            context,
                            "\uD83D\uDE28 Wooops $result.message}",
                            Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun initSearch() {
        updateRepoListFromInput()
        true
    }

    private fun updateRepoListFromInput() {

                binding.list.scrollToPosition(0)
                viewModel.searchRepo()

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


    override fun onAttach(context: Context) {
        Log.d(TAG,"onAttach")
        super.onAttach(context)
        this.context = context as AppCompatActivity
    }



}