package com.deepshikhayadav.geetacollege.ui.pedometer

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.deepshikhayadav.geetacollege.databinding.FragmentPedometerBinding
import com.deepshikhayadav.geetacollege.ui.pedometer.listener.StepListener
import com.deepshikhayadav.geetacollege.ui.pedometer.utils.StepDetector
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlinx.android.synthetic.main.fragment_pedometer.*

class PedometerFragment : Fragment(), SensorEventListener, StepListener {
    private var _binding: FragmentPedometerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var simpleStepDetector: StepDetector? = null
    private var sensorManager: SensorManager? = null
    private val TEXT_NUM_STEPS = " "
    private var numSteps: Int = 0
    lateinit var progressCircular : CircularProgressBar

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector!!.updateAccelerometer(event.timestamp, event.values[0], event.values[1], event.values[2])
        }
    }

    override fun step(timeNs: Long) {
        numSteps++
        binding.tvSteps.text = TEXT_NUM_STEPS.plus(numSteps)
        progressCircular.apply { setProgressWithAnimation(numSteps.toFloat()) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(PedometerViewModel::class.java)

        _binding = FragmentPedometerBinding.inflate(inflater, container, false)
        val root: View = binding.root
        progressCircular = binding.circularProgressBar

        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
         //   getSystemService(Context.SENSOR_SERVICE) as SensorManager
        simpleStepDetector = StepDetector()
        simpleStepDetector!!.registerListener(this)

        binding.btnStart.setOnClickListener {
            numSteps = 0
            sensorManager!!.registerListener(this, sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST)
        }

        binding.btnStop.setOnClickListener {
            sensorManager!!.unregisterListener(this)
            binding.tvSteps.text= "0"
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


/*
 SensorEventListener, StepListener {
    private var simpleStepDetector: StepDetector? = null
    private var sensorManager: SensorManager? = null
    private val TEXT_NUM_STEPS = "Number of Steps: "
    private var numSteps: Int = 0

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector!!.updateAccelerometer(event.timestamp, event.values[0], event.values[1], event.values[2])
        }
    }

    override fun step(timeNs: Long) {
        numSteps++
        tvSteps.text = TEXT_NUM_STEPS.plus(numSteps)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get an instance of the SensorManager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        simpleStepDetector = StepDetector()
        simpleStepDetector!!.registerListener(this)

        btnStart.setOnClickListener(View.OnClickListener {
            numSteps = 0
            sensorManager!!.registerListener(this, sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST)
        })

        btnStop.setOnClickListener(View.OnClickListener {
            sensorManager!!.unregisterListener(this)
        })
    }
}

* */