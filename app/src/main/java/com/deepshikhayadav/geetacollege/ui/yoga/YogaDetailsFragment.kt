package com.deepshikhayadav.geetacollege.ui.yoga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deepshikhayadav.geetacollege.R
import com.deepshikhayadav.geetacollege.adapter.YogaAdapter
import com.deepshikhayadav.geetacollege.databinding.FragmentYogaDetailsBinding
import com.deepshikhayadav.geetacollege.databinding.YogaFragmentBinding
import com.google.android.material.textview.MaterialTextView

class YogaDetailsFragment : Fragment() {
    private val navigationArgs: YogaDetailsFragmentArgs by navArgs()
    lateinit var head: String
    lateinit var desc :String
    lateinit var image :String
    private var _binding : FragmentYogaDetailsBinding ?= null
    private val binding get() = _binding!!
    private lateinit var descTv :MaterialTextView
    private lateinit var headingTv : MaterialTextView
    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentYogaDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        descTv = binding.yogaDesc
        headingTv = binding.yogaTitle
        imageView = binding.imageYoga

        head = navigationArgs.yoga!!.heading
        desc = navigationArgs.yoga!!.desc
        image = navigationArgs.yoga!!.image
        showItem()
        return root
    }

    private fun showItem() {
        Glide.with(requireContext()).load(IMAGE_BASE_URL + image)
            .into(imageView)
        descTv.text= desc
        headingTv.text = head
    }
    companion object {
        private const val IMAGE_BASE_URL =
            "https://deepu-api-health-freak.herokuapp.com/uploads/"
    }



}