package com.deepshikhayadav.geetacollege.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.deepshikhayadav.geetacollege.MainActivity
import com.deepshikhayadav.geetacollege.databinding.FragmentHomeBinding
import com.firebase.ui.auth.AuthUI
import me.itangqi.waveloadingview.WaveLoadingView

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var waterLevelView: WaveLoadingView
    lateinit var minusGlass :ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        waterLevelView = binding.waterLevelView

        binding.btnStats.setOnClickListener {
            AuthUI.getInstance().signOut(requireActivity()).addOnSuccessListener {
                Toast.makeText(activity,"Log Out",Toast.LENGTH_SHORT).show()
                startActivity(Intent(activity, MainActivity::class.java))
            }
        }
        initView()
        return root
    }

    private fun initView() {
        binding.op50ml.setOnClickListener {
            waterLevelView.progressValue=70
        }
        binding.op100ml.setOnClickListener {

        }
        binding.op150ml.setOnClickListener {

        }
        binding.op200ml.setOnClickListener {

        }
        binding.op250ml.setOnClickListener {

        }
        binding.opCustom.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}