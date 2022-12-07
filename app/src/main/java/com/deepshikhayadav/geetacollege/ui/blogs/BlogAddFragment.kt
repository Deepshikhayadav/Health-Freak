package com.deepshikhayadav.geetacollege.ui.blogs

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory.decodeFile
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.deepshikhayadav.geetacollege.databinding.FragmentBlogAddBinding
import com.deepshikhayadav.geetacollege.local_db.MyDatabase
import com.deepshikhayadav.geetacollege.retrofit.RetrofitBuilder
import com.deepshikhayadav.geetacollege.retrofit.remote.BlogRepositoryimpl
import com.deepshikhayadav.geetacollege.util.MainViewModelFactory
import com.deepshikhayadav.geetacollege.util.NetworkHelper
import com.deepshikhayadav.geetacollege.util.Utils
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream


class BlogAddFragment : Fragment() {

    private lateinit var viewModel: BlogsViewModel

    private var _binding: FragmentBlogAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var etHead : EditText
    private lateinit var etDesc : EditText
    private lateinit var ivImg : ImageView
    private lateinit var btnSubmit : Button
    private lateinit var  file :File
    private lateinit var  imageStoragePath : String
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
            if (ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            getImage()
        }
        btnSubmit.setOnClickListener {
            getData()
        }

        return root
    }

    private fun getData() {
       // showProgress()
        val head = etHead.text.toString()
        val desc = etDesc.text.toString()
        val author = "Deepu"

        viewModel = ViewModelProvider(
            this, MainViewModelFactory(
                NetworkHelper(requireContext()),
                BlogRepositoryimpl(
                    MyDatabase.getInstance(requireContext()).myDao(),
                    RetrofitBuilder.buildService()
                )
            )
        )[BlogsViewModel::class.java]
        viewModel.blogUpload(head,desc,author,file)


        viewModel.postResponse.observe(requireActivity(), Observer {
            showMovies(it.message)
           // hideProgress()
        })

        viewModel.errorResponse.observe(requireActivity(), Observer {
            showErrorMessage(it)
           // hideProgress()
        })
    }

    private fun showErrorMessage(it: String) {
    Log.i("error",it)
    }

    private fun showMovies(message: String) {
        Toast.makeText(activity,message,Toast.LENGTH_SHORT).show()
    }


    private fun getImage() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, pickImage)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            val imageUri = data?.data
          file = File(imageUri!!.path)
           if(file!= null){
               imageStoragePath = file.path
                imageStoragePath = decodeFile(imageStoragePath)!!
               Toast.makeText(activity, imageStoragePath,Toast.LENGTH_SHORT).show()
           }

          /*  file = File(
                imageUri.path + File.separator
                        + "IMG_"  + "." + "png"
            )
            Toast.makeText(activity, file.absolutePath.toString(),Toast.LENGTH_SHORT).show()*/

            Log.i("error",file.absolutePath.toString())
            ivImg.setImageURI(imageUri)
        }
    }

    private fun decodeFile(path: String): String? {
        var strMyImagePath: String? = null
        var scaledBitmap: Bitmap? = null
        try {
            // Part 1: Decode image
            val unscaledBitmap: Bitmap = Utils.decodeFile(path, 100, 100, Utils.Companion.ScalingLogic.FIT)
            scaledBitmap = if (!(unscaledBitmap.width <= 800 && unscaledBitmap.height <= 800)) {
                // Part 2: Scale image
                Utils.createScaledBitmap(unscaledBitmap, 100, 100, Utils.Companion.ScalingLogic.FIT)
            } else {
                unscaledBitmap.recycle()
                return path
            }

            // Store to tmp file
            // File mFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/QrScan");
            val extr = Environment.getExternalStorageDirectory().toString()
            val mFolder = File("$extr/myTmpDir")
            if (!mFolder.exists()) {
                mFolder.mkdir()
            }
            val s = "qr.png"
            val f = File(mFolder.absolutePath, s)
            strMyImagePath = f.absolutePath
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(f)
                scaledBitmap!!.compress(Bitmap.CompressFormat.PNG, 20, fos)
                fos.flush()
                fos.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            scaledBitmap!!.recycle()
        } catch (e: Throwable) {
        }
        return strMyImagePath ?: path
    }
}