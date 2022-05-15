package com.deepshikhayadav.geetacollege.ui.blogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import com.deepshikhayadav.geetacollege.adapter.BlogAdapter
import com.deepshikhayadav.geetacollege.databinding.FragmentBlogsBinding
import com.deepshikhayadav.geetacollege.local_db.BlogDatabase
import com.deepshikhayadav.geetacollege.local_db.entity.BLog
import com.deepshikhayadav.geetacollege.retrofit.RetrofitBuilder
import com.deepshikhayadav.geetacollege.retrofit.remote.BlogRepositoryimpl
import com.deepshikhayadav.geetacollege.util.MainViewModelFactory
import com.deepshikhayadav.geetacollege.util.NetworkHelper
import kotlinx.android.synthetic.main.fragment_blogs.*

class BlogsFragment : Fragment() {

    private lateinit var viewModel: BlogsViewModel

    private var _binding: FragmentBlogsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*val notificationsViewModel =
            ViewModelProvider(this).get(BlogsViewModel::class.java)*/

        _binding = FragmentBlogsBinding.inflate(inflater, container, false)
        val root: View = binding.root

     /*   val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        binding.fabAdd.setOnClickListener {

        }
        setupViewModel()
        observeViewModel()
        return root
    }
    private fun setupViewModel() {
      //  showProgress()

        viewModel = ViewModelProvider(
            this, MainViewModelFactory(
                NetworkHelper(requireContext()),
                BlogRepositoryimpl(
                    BlogDatabase.getInstance(requireContext()).movieDao(),
                    RetrofitBuilder.buildService()
                )
            )
        )[BlogsViewModel::class.java]
        viewModel.onCreate()
    }

    private fun observeViewModel() {
        viewModel.myResponse.observe(requireActivity(), Observer {
            showMovies(it.data)
           // hideProgress()
        })

        viewModel.errorResponse.observe(requireActivity(), Observer {
            showErrorMessage(it)
            hideProgress()
        })
    }

    private fun showMovies(movies: List<BLog>) {
        binding.blogRecycler.visibility = View.VISIBLE
        binding.blogRecycler.setHasFixedSize(true)
        binding.blogRecycler.itemAnimator = DefaultItemAnimator()
        binding.blogRecycler.adapter = BlogAdapter(movies)
    }

    private fun showErrorMessage(errorMessage: String?) {
        errorView.visibility = View.VISIBLE
        errorView.text = errorMessage
    }

    private fun hideProgress() {
        //progressBar.visibility = View.GONE
    }

    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}