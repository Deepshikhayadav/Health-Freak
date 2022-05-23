package com.deepshikhayadav.geetacollege.ui.blogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.deepshikhayadav.geetacollege.R
import com.deepshikhayadav.geetacollege.adapter.BlogAdapter
import com.deepshikhayadav.geetacollege.adapter.YogaAdapter
import com.deepshikhayadav.geetacollege.databinding.FragmentBlogsBinding
import com.deepshikhayadav.geetacollege.local_db.MyDatabase
import com.deepshikhayadav.geetacollege.local_db.entity.BLog
import com.deepshikhayadav.geetacollege.retrofit.RetrofitBuilder
import com.deepshikhayadav.geetacollege.retrofit.remote.BlogRepositoryimpl
import com.deepshikhayadav.geetacollege.ui.yoga.YogaFragmentDirections
import com.deepshikhayadav.geetacollege.util.MainViewModelFactory
import com.deepshikhayadav.geetacollege.util.NetworkHelper


class BlogsFragment : Fragment() {

    private lateinit var viewModel: BlogsViewModel

    private var _binding: FragmentBlogsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerView : RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       /* viewModel =
            ViewModelProvider(this).get(BlogsViewModel::class.java)*/

        _binding = FragmentBlogsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = binding.blogRecycler
        progressBar = binding.progressBar


        binding.fabAdd.setOnClickListener {
            val action = BlogsFragmentDirections.actionNavigationBlogToBlogAddFragment(/*getString(R.string.add)*/)
            this.findNavController().navigate(action)
        }

        return root
    }


    private fun setupViewModel() {
        showProgress()

        viewModel = ViewModelProvider(
            this, MainViewModelFactory(
                NetworkHelper(requireContext()),
                BlogRepositoryimpl(
                    MyDatabase.getInstance(requireContext()).myDao(),
                    RetrofitBuilder.buildService()
                )
            )
        )[BlogsViewModel::class.java]
        viewModel.onCreate()
    }

    private fun observeViewModel() {
        val adapter = BlogAdapter {
            val action =
                BlogsFragmentDirections.actionNavigationBlogToBlogDetailsFragment(it)
            this.findNavController().navigate(action)

        }
        recyclerView.visibility = View.VISIBLE
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter =  adapter

        viewModel.myResponse.observe(requireActivity(), Observer { items->
            items.let {
                Log.i("data","${it.data[0]} ")
                adapter.submitList(it.data)
            }
            hideProgress()
        })


       /* viewModel.myResponse.observe(requireActivity(), Observer {
            showBlog(it.data)
            hideProgress()
        })
*/
        viewModel.errorResponse.observe(requireActivity(), Observer {
            showErrorMessage(it)
            hideProgress()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        observeViewModel()
    }

    /*  private fun showBlog(movies: List<BLog>) {
        recyclerView.visibility = View.VISIBLE
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = BlogAdapter(movies)
    }*/

    private fun showErrorMessage(errorMessage: String?) {
      /*  errorView.visibility = View.VISIBLE
        errorView.text = errorMessage*/
    }

    private fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}