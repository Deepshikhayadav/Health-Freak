package com.deepshikhayadav.geetacollege.ui.yoga

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.deepshikhayadav.geetacollege.R
import com.deepshikhayadav.geetacollege.adapter.BlogAdapter
import com.deepshikhayadav.geetacollege.adapter.YogaAdapter
import com.deepshikhayadav.geetacollege.databinding.FragmentPedometerBinding
import com.deepshikhayadav.geetacollege.databinding.YogaFragmentBinding
import com.deepshikhayadav.geetacollege.local_db.MyDatabase
import com.deepshikhayadav.geetacollege.local_db.entity.BLog
import com.deepshikhayadav.geetacollege.local_db.entity.Yoga
import com.deepshikhayadav.geetacollege.retrofit.RetrofitBuilder
import com.deepshikhayadav.geetacollege.retrofit.remote.BlogRepositoryimpl
import com.deepshikhayadav.geetacollege.ui.blogs.BlogsViewModel
import com.deepshikhayadav.geetacollege.ui.pedometer.PedometerViewModel
import com.deepshikhayadav.geetacollege.util.MainViewModelFactory
import com.deepshikhayadav.geetacollege.util.MainViewModelFactory2
import com.deepshikhayadav.geetacollege.util.NetworkHelper
import kotlinx.android.synthetic.main.fragment_blogs.*

class YogaFragment : Fragment() {
    private var _binding: YogaFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private lateinit var viewModel: YogaViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       /* val yogaViewModel =
            ViewModelProvider(this).get(YogaViewModel::class.java)*/

        _binding = YogaFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        recyclerView = binding.yogaRecycler
        progressBar = binding.progressBar

        setupViewModel()
        observeViewModel()
        return root
    }
    private fun setupViewModel() {
        showProgress()

        viewModel = ViewModelProvider(
            this, MainViewModelFactory2(
                NetworkHelper(requireContext()),
                BlogRepositoryimpl(
                    MyDatabase.getInstance(requireContext()).myDao(),
                    RetrofitBuilder.buildService()
                )
            )
        )[YogaViewModel::class.java]
        viewModel.onCreate()
    }

    private fun observeViewModel() {
        viewModel.myResponse.observe(requireActivity(), Observer {
            showYoga(it.data)
            hideProgress()
        })

        viewModel.errorResponse.observe(requireActivity(), Observer {
            showErrorMessage(it)
            hideProgress()
        })
    }

    private fun showYoga(yoga: List<Yoga>) {
        recyclerView.visibility = View.VISIBLE
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = YogaAdapter(yoga)
    }

    private fun showErrorMessage(errorMessage: String?) {
        errorView.visibility = View.VISIBLE
        errorView.text = errorMessage
    }

    private fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

   /* override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(YogaViewModel::class.java)
        // TODO: Use the ViewModel
    }
*/
   override fun onDestroyView() {
       super.onDestroyView()
       _binding = null
   }
}