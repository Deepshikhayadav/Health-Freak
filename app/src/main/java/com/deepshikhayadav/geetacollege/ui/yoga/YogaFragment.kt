package com.deepshikhayadav.geetacollege.ui.yoga

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.deepshikhayadav.geetacollege.R
import com.deepshikhayadav.geetacollege.databinding.FragmentPedometerBinding
import com.deepshikhayadav.geetacollege.databinding.YogaFragmentBinding
import com.deepshikhayadav.geetacollege.ui.pedometer.PedometerViewModel

class YogaFragment : Fragment() {
    private var _binding: YogaFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = YogaFragment()
    }

    //private lateinit var viewModel: YogaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val yogaViewModel =
            ViewModelProvider(this).get(YogaViewModel::class.java)

        _binding = YogaFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

    /*    val textView: TextView = binding.textYoga
        yogaViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        return root
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