package com.deepshikhayadav.geetacollege

import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.deepshikhayadav.geetacollege.databinding.ActivityEnterDataBinding
import com.deepshikhayadav.geetacollege.databinding.ActivityMainBinding
import com.deepshikhayadav.geetacollege.util.Utils
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*

class EnterDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEnterDataBinding
    private var weight: String = ""
    private var workTime: String = ""
    private var wakeupTime: Long = 0
    private var sleepingTime: Long = 0
    private lateinit var sharedPref: SharedPreferences
    private var doubleBackToExitPressedOnce = false
    lateinit var etWakeUpTime : TextInputLayout
    lateinit var etSleepTime : TextInputLayout
    lateinit var btnContinue : Button
    lateinit var etWeight :TextInputLayout
    lateinit var etWorkTime : TextInputLayout
    private lateinit var parentLayout :ConstraintLayout
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val is24h = android.text.format.DateFormat.is24HourFormat(this.applicationContext)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        binding = ActivityEnterDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btnContinue = binding.btnContinue
        etWakeUpTime= binding.etWakeUpTime
        etSleepTime = binding.etSleepTime
        etWeight = binding.etWeight
        etWorkTime = binding.etWorkTime
        parentLayout = binding.parentLayout
        
        setClickEvent(is24h)
    }

    private fun setClickEvent(is24h: Boolean) {
        sharedPref = getSharedPreferences(Utils.USERS_SHARED_PREF, Utils.PRIVATE_MODE)

        wakeupTime = sharedPref.getLong(Utils.WAKEUP_TIME, 1558323000000)
        sleepingTime = sharedPref.getLong(Utils.SLEEPING_TIME_KEY, 1558369800000)

        etWakeUpTime.editText!!.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = wakeupTime

            val mTimePicker = TimePickerDialog(
                this,
                { _, selectedHour, selectedMinute ->

                    val time = Calendar.getInstance()
                    time.set(Calendar.HOUR_OF_DAY, selectedHour)
                    time.set(Calendar.MINUTE, selectedMinute)
                    wakeupTime = time.timeInMillis

                    etWakeUpTime.editText!!.setText(
                        String.format("%02d:%02d", selectedHour, selectedMinute)
                    )
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), is24h
            )
            mTimePicker.setTitle("Select Wakeup Time")
            mTimePicker.show()
        }
        etSleepTime.editText!!.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = sleepingTime

            val mTimePicker = TimePickerDialog(
                this,
                { _, selectedHour, selectedMinute ->

                    val time = Calendar.getInstance()
                    time.set(Calendar.HOUR_OF_DAY, selectedHour)
                    time.set(Calendar.MINUTE, selectedMinute)
                    sleepingTime = time.timeInMillis

                    etSleepTime.editText!!.setText(
                        String.format("%02d:%02d", selectedHour, selectedMinute)
                    )
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), is24h
            )
            mTimePicker.setTitle("Select Sleeping Time")
            mTimePicker.show()
        }

        btnContinue.setOnClickListener {

            val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(parentLayout.windowToken, 0)

            weight = etWeight.editText!!.text.toString()
            workTime = etWorkTime.editText!!.text.toString()

            when {
                TextUtils.isEmpty(weight) -> Snackbar.make(it, "Please input your weight", Snackbar.LENGTH_SHORT).show()
                weight.toInt() > 200 || weight.toInt() < 20 -> Snackbar.make(
                    it,
                    "Please input a valid weight",
                    Snackbar.LENGTH_SHORT
                ).show()
                TextUtils.isEmpty(workTime) -> Snackbar.make(
                    it,
                    "Please input your workout time",
                    Snackbar.LENGTH_SHORT
                ).show()
                workTime.toInt() > 500 || workTime.toInt() < 0 -> Snackbar.make(
                    it,
                    "Please input a valid workout time",
                    Snackbar.LENGTH_SHORT
                ).show()
                else -> {

                    val editor = sharedPref.edit()
                    editor.putInt(Utils.WEIGHT_KEY, weight.toInt())
                    editor.putInt(Utils.WORK_TIME_KEY, workTime.toInt())
                    editor.putLong(Utils.WAKEUP_TIME, wakeupTime)
                    editor.putLong(Utils.SLEEPING_TIME_KEY, sleepingTime)
                    editor.putBoolean(Utils.FIRST_RUN_KEY, false)

                    val totalIntake = Utils.calculateIntake(weight.toInt(), workTime.toInt())
                    val df = DecimalFormat("#")
                    df.roundingMode = RoundingMode.CEILING
                    editor.putInt(Utils.TOTAL_INTAKE, df.format(totalIntake).toInt())

                    editor.apply()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()

                }
            }
        }

    }
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Snackbar.make(
            this.window.decorView.findViewById(android.R.id.content),
            "Please click BACK again to exit",
            Snackbar.LENGTH_SHORT
        ).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 1000)
    }

}