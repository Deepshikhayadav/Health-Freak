package com.deepshikhayadav.geetacollege.ui.blogs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.deepshikhayadav.geetacollege.R
import com.deepshikhayadav.geetacollege.databinding.FragmentBlogDetailsBinding
import com.deepshikhayadav.geetacollege.databinding.FragmentYogaDetailsBinding
import com.deepshikhayadav.geetacollege.ui.yoga.YogaDetailsFragmentArgs
import com.google.android.material.textview.MaterialTextView


class BlogDetailsFragment : Fragment() {
    private val navigationArgs: BlogDetailsFragmentArgs by navArgs()
    lateinit var head: String
    lateinit var desc :String
    lateinit var image :String
    private var _binding : FragmentBlogDetailsBinding?= null
    private val binding get() = _binding!!
    private lateinit var descTv : MaterialTextView
    private lateinit var headingTv : MaterialTextView
    private lateinit var authorTv : MaterialTextView
    private lateinit var dateTv : MaterialTextView
    private lateinit var imageView: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBlogDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        descTv = binding.blogDesc
        headingTv = binding.blogTitle
        imageView = binding.blogImage
        authorTv = binding.tvAuthor
        dateTv = binding.tvDate

        head = navigationArgs.blog!!.heading
        desc = navigationArgs.blog!!.desc
        image = navigationArgs.blog!!.image
        showItem()
        return root
    }

    private fun showItem() {

        Glide.with(requireContext()).load(IMAGE_BASE_URL + image)
            .into(imageView)
        descTv.text= desc
        headingTv.text = head
        authorTv.text = "~"+navigationArgs.blog!!.author
        //dateTv.text = navigationArgs.blog!!.
    }
    companion object {
        private const val IMAGE_BASE_URL =
            "https://deepu-api-health-freak.herokuapp.com/uploads/"
    }

}