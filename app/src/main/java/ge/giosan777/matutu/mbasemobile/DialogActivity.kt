package ge.giosan777.matutu.mbasemobile

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class DialogActivity : ComponentActivity() {
    lateinit var user: String
    lateinit var sensorManager: SensorManager
    var proximitySensor: Sensor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        intent.extras?.getString("user")?.let { it_string -> user = it_string }
        Log.d("MyLog", "222")


        setContent {
            Card(
                modifier = Modifier
                    .fillMaxSize(),
                shape = RoundedCornerShape(bottomStart = 32.dp, topEnd = 32.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.DarkGray),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(modifier=Modifier.padding(start=12.dp),
                        text = user, fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Normal, fontSize = 18.sp,
                        color = Color.White
                    )
                    Button(
                        onClick = { this@DialogActivity.finish() },
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Text(text = "Ok")
                    }
                }
            }
        }


        this.window.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY)
        setFinishOnTouchOutside(true)
        this.window.setLayout(width - 100, 160)
        this.window.setDimAmount(0F)
        this.window.setFormat(PixelFormat.TRANSLUCENT)
        this.window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        this.window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        this.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


    }

    var proximitySensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(sensorEvent: SensorEvent) {
            if (proximitySensor != null) {
                if (sensorEvent.values[0] < proximitySensor!!.getMaximumRange()) {
                    finish()
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, i: Int) {}
    }


    private val myReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if ("ACTION_DAKETVA_ACTIVITY" == intent.action) {
                finish()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        val filter = IntentFilter("ACTION_DAKETVA_ACTIVITY")
        registerReceiver(myReceiver, filter)
        sensorManager.registerListener(
            proximitySensorListener,
            proximitySensor, 2 * 1000 * 1000
        );
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(myReceiver);
        sensorManager.unregisterListener(proximitySensorListener);

    }


}


