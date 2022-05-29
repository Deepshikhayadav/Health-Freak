package com.deepshikhayadav.geetacollege.ui.home

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.deepshikhayadav.geetacollege.MainActivity
import com.deepshikhayadav.geetacollege.R
import com.deepshikhayadav.geetacollege.databinding.FragmentHomeBinding
import com.deepshikhayadav.geetacollege.util.Utils
import com.firebase.ui.auth.AuthUI
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import me.itangqi.waveloadingview.WaveLoadingView

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // activity property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var waterLevelView: WaveLoadingView
    lateinit var minusGlass: ImageView
    lateinit var sharedPref: SharedPreferences
    var myDailyIntake = 0
    lateinit var fabAdd: FloatingActionButton
    private var snackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       /* val homeViewModel =
            ViewModelProvider(activity).get(HomeViewModel::class.java)*/

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        waterLevelView = binding.waterLevelView
        sharedPref =
            requireActivity().getSharedPreferences(Utils.USERS_SHARED_PREF, Utils.PRIVATE_MODE)

        binding.btnStats.setOnClickListener {
            AuthUI.getInstance().signOut(requireActivity()).addOnSuccessListener {
                Toast.makeText(activity, "Log Out", Toast.LENGTH_SHORT).show()
                startActivity(Intent(activity, MainActivity::class.java))
            }
        }
        fabAdd = binding.fabAdd

        initView()

        return root
    }

    private fun initView() {
        val totalIn = sharedPref.getInt(
            Utils.TOTAL_INTAKE,
            0
        )
        val weight = sharedPref.getInt(
            Utils.WEIGHT_KEY,
            0
        )
        val work = sharedPref.getInt(
            Utils.WORK_TIME_KEY,
            0
        )
        val wake = sharedPref.getLong(
            Utils.WAKEUP_TIME,
            0
        )
        val sleep = sharedPref.getLong(
            Utils.SLEEPING_TIME_KEY,
            0
        )


        binding.tvTotalIntake.text= "/$totalIn ml"
       // binding.tvIntook.text = myDailyIntake.toString()

        val outValue = TypedValue()
        requireContext().theme.resolveAttribute(
            android.R.attr.selectableItemBackground,
            outValue,
            true
        )

        binding.op50ml.setOnClickListener {
            if (snackbar != null) {
                snackbar?.dismiss()
            }
            myDailyIntake += 50
            binding.op50ml.background = requireActivity().getDrawable(R.drawable.option_select_bg)
            binding.op100ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.op150ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.op200ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.op250ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.opCustom.background = requireActivity().getDrawable(outValue.resourceId)
        }
        binding.op100ml.setOnClickListener {
            if (snackbar != null) {
                snackbar?.dismiss()
            }
            myDailyIntake += 100
            binding.op50ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.op100ml.background = requireActivity().getDrawable(R.drawable.option_select_bg)
            binding.op150ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.op200ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.op250ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.opCustom.background = requireActivity().getDrawable(outValue.resourceId)
        }
        binding.op150ml.setOnClickListener {
            if (snackbar != null) {
                snackbar?.dismiss()
            }
            myDailyIntake += 150
            binding.op50ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.op100ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.op150ml.background = requireActivity().getDrawable(R.drawable.option_select_bg)
            binding.op200ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.op250ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.opCustom.background = requireActivity().getDrawable(outValue.resourceId)
        }
        binding.op200ml.setOnClickListener {
            if (snackbar != null) {
                snackbar?.dismiss()
            }
            myDailyIntake += 200
            binding.op50ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.op100ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.op150ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.op200ml.background = requireActivity().getDrawable(R.drawable.option_select_bg)
            binding.op250ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.opCustom.background = requireActivity().getDrawable(outValue.resourceId)
        }
        binding.op250ml.setOnClickListener {
            if (snackbar != null) {
                snackbar?.dismiss()
            }
            myDailyIntake += 250
            binding.op50ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.op100ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.op150ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.op200ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.op250ml.background = requireActivity().getDrawable(R.drawable.option_select_bg)
            binding.opCustom.background = requireActivity().getDrawable(outValue.resourceId)
        }
        binding.opCustom.setOnClickListener {
            if (snackbar != null) {
                snackbar?.dismiss()
            }
            val li = LayoutInflater.from(activity)
            val promptsView = li.inflate(R.layout.custom_input_dialog, null)

            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setView(promptsView)

            val userInput = promptsView
                .findViewById(R.id.etCustomInput) as TextInputLayout

            alertDialogBuilder.setPositiveButton("OK") { dialog, id ->
                val inputText = userInput.editText!!.text.toString()
                if (!TextUtils.isEmpty(inputText)) {
                    binding.tvIntook.text = "${inputText} ml"
                    myDailyIntake += inputText.toInt()
                }
            }.setNegativeButton("Cancel") { dialog, id ->
                dialog.cancel()
            }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()

            binding.op50ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.op100ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.op150ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.op200ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.op250ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.opCustom.background = requireActivity().getDrawable(R.drawable.option_select_bg)


        }
        fabAdd.setOnClickListener {
            val p = ((myDailyIntake * 100) / totalIn)
            Log.i("deepu", "$p $myDailyIntake $totalIn")
            waterLevelView.progressValue = p
            waterLevelView.centerTitle = "$p%"

            binding.tvIntook.text = "${myDailyIntake}"

            binding.op50ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.op100ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.op150ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.op200ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.op250ml.background = requireActivity().getDrawable(outValue.resourceId)
            binding.opCustom.background = requireActivity().getDrawable(outValue.resourceId)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()

    }
}