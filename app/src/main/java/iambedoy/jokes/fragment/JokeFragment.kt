package iambedoy.jokes.fragment

import android.content.Context.SENSOR_SERVICE
import android.content.Intent
import android.hardware.Sensor
import android.hardware.Sensor.TYPE_ACCELEROMETER
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import iambedoy.jokes.response.JokeResponse
import iambedoy.jokes.viewmodel.JokeViewModel
import iambedoy.jokes.R
import kotlinx.android.synthetic.main.fragment_joke.*
import org.koin.android.ext.android.inject
import kotlin.math.pow
import kotlin.math.sqrt


/**
 * Random Jokes
 *
 * Created by bedoy on 01/07/20.
 */
class JokeFragment : Fragment(), SensorEventListener{

    private val threshold = 5.0 // m/S**2
    private val timeOffset = 3500

    private var lastShakeTime : Long = 0

    private val colors = listOf(
        R.color.color_primary_01,
        R.color.color_primary_02,
        R.color.color_primary_03,
        R.color.color_primary_04,
        R.color.color_primary_05,
        R.color.color_primary_06,
        R.color.color_primary_07,
        R.color.color_primary_08,
        R.color.color_primary_09
    )

    private val viewModel : JokeViewModel by inject()

    private val sensorManager by lazy {
        context?.getSystemService(SENSOR_SERVICE) as? SensorManager
    }

    private val sensor by lazy {
        sensorManager?.getDefaultSensor(TYPE_ACCELEROMETER)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_joke, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.currentJoke.observe(viewLifecycleOwner, Observer { joke ->

            context?.let {
                joke_title.setTextColor(ContextCompat.getColor(it, colors.shuffled().first()))
            }

            joke_title.text = joke.setup
            joke_description.text = joke.punchline
            joke_hash_tag.setText(
                if(joke.setup.length % 2 == 0) R.string.credit else R.string.credit_alt
            )

            view.setOnClickListener { shareContent(joke) }
            joke_title.setOnClickListener { shareContent(joke) }
            joke_description.setOnClickListener { shareContent(joke) }
        })


    }

    private fun shareContent(joke: JokeResponse) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "${joke.setup} ${joke.punchline}")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun onResume() {
        super.onResume()

        sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        viewModel.takeRandomJoke()
    }

    override fun onPause() {
        super.onPause()

        sensorManager?.unregisterListener(this)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        sensorEvent?.let {
            if (sensor?.type == TYPE_ACCELEROMETER) {
                val curTime = System.currentTimeMillis()
                if (curTime - lastShakeTime > timeOffset) {
                    val x: Float = sensorEvent.values[0]
                    val y: Float = sensorEvent.values[1]
                    val z: Float = sensorEvent.values[2]
                    val acceleration = sqrt(
                        x.toDouble().pow(2.0) +
                                y.toDouble().pow(2.0) +
                                z.toDouble().pow(2.0)
                    ) - SensorManager.GRAVITY_EARTH
                    Log.d("JokeFragment", "${acceleration * 0.3} -> $threshold")
                    if (acceleration * 0.3 > threshold) {
                        //Log.d("JokeFragment", "Shake, Rattle, and Roll")

                        lastShakeTime = curTime

                        viewModel.takeRandomJoke()

                    }
                }
            }
        }
    }


}