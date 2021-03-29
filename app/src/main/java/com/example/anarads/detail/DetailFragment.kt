package com.example.anarads.detail

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.anarads.R
import com.example.anarads.comments.CommentsFragment
import com.example.anarads.comments.CreateComment
import com.example.anarads.model.ImagesSlider
import kotlinx.android.synthetic.main.blank_fragment.*

class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()
    lateinit var context: AppCompatActivity

    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var viewModel: DetailViewModel
    val imageList = ArrayList<SlideModel>()

    override fun onAttach(context: Context) {
        Log.d(TAG,"onAttach")
        super.onAttach(context)
        this.context = context as AppCompatActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.blank_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        val id_post = args.PostId



        viewModel.productDetails.observe(viewLifecycleOwner, Observer {
            product_name.text = it.title
            thePriceOfProduct.text = "$${it.price}"
            theDescription.text = it.description



            for(item in it.images_slider){
                imageList.add(SlideModel(item.original, ""))
            }
            image_slider.setImageList(imageList)
        })

        viewModel.fetchProductDetails(id_post)


//        imageList.add(SlideModel("https://3.bp.blogspot.com/-Wxr9iElgMHc/XvEleXaZOTI/AAAAAAAAAQY/WcZotaCBqdYQzBX5HBjTr1R3NkiKtS1sgCLcBGAsYHQ/s1600/lion.png", ""))
//        imageList.add(SlideModel("https://3.bp.blogspot.com/-Wxr9iElgMHc/XvEleXaZOTI/AAAAAAAAAQY/WcZotaCBqdYQzBX5HBjTr1R3NkiKtS1sgCLcBGAsYHQ/s1600/lion.png", ""))

////        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)


        val secondFragment = CommentsFragment.newInstance(id_post)
        openFragment(secondFragment)

        val nextFragment = CreateComment.newInstance(id_post)
        openCommentCreateFragment(nextFragment)
    }


    private fun openFragment(fragment: Fragment) {
        val transaction = context.getSupportFragmentManager().beginTransaction()
        transaction.replace(R.id.frame_comment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun openCommentCreateFragment(fragment: Fragment) {
        val transaction = context.getSupportFragmentManager().beginTransaction()
        transaction.replace(R.id.frame_create_comment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}