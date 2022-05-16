package com.deepshikhayadav.geetacollege.ui.blogs

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.deepshikhayadav.geetacollege.databinding.FragmentBlogAddBinding
import java.io.File


class BlogAddFragment : Fragment() {
    private var _binding: FragmentBlogAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var etHead : EditText
    private lateinit var etDesc : EditText
    private lateinit var ivImg : ImageView
    private lateinit var btnSubmit : Button
    private lateinit var  file :File
    val pickImage= 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      //  return inflater.inflate(R.layout.fragment_blog_add, container, false)
        _binding = FragmentBlogAddBinding.inflate(inflater, container, false)
        val root: View = binding.root
        etHead = binding.etHead
        etDesc = binding.etDesc
        ivImg = binding.imageView
        btnSubmit = binding.btnSubmit

        ivImg.setOnClickListener {
            getImage()
        }
        btnSubmit.setOnClickListener {
            getData()
        }

        return root
    }

    private fun getData() {
        val head = etHead.text.toString()
        val desc = etDesc.text.toString()
       // Toast.makeText(activity, "$head $desc",Toast.LENGTH_SHORT).show()

    }

    private fun getImage() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, pickImage)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            val imageUri = data?.data
          file = File(imageUri!!.getPath())
           // Toast.makeText(activity, file.absolutePath.toString(),Toast.LENGTH_SHORT).show()
            ivImg.setImageURI(imageUri)
        }
    }
}