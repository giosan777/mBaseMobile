package ge.giosan777.matutu.mbasemobile.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import ge.giosan777.matutu.mbasemobile.Volley.mobileBase.getOneContactExcept

class CallsService : Service() {
    var context: Context = this
    var handler: Handler? = null
    var runnable: Runnable? = null
    var running = false

    var MyServiceReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            if (phoneState == TelephonyManager.EXTRA_STATE_RINGING) {
                val incomingNumber = intent.getStringExtra(TelephonyManager. EXTRA_INCOMING_NUMBER)
                println(incomingNumber)
                incomingNumber?.let {
                    getOneContactExcept(incomingNumber,context){
                        Toast.makeText(context, "Call $it", Toast.LENGTH_LONG).show()
                        val i = Intent()
                        i.setClassName(
                            "ge.giosan777.matutu.mbasemobile",
                            "ge.giosan777.matutu.mbasemobile.SecondActivity"
                        )
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        i.putExtra("number", "$incomingNumber")
                        i.putExtra("user", it)
                        context.startActivity(i)
                    }
                }



            }
//                            Toast.makeText(getApplicationContext(), "Toast from Service: I hear you!", Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        // начало отличия от обычного сервиса
        // Таковы требования Андроида: хочешь полноценно работать в фоне - нужно держать уведомление и иметь доп разрешение в манифесте
        val CHANNEL_ID = "my_channel_01"
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Channel human readable title",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
            channel
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("")
            .setContentText("").build()
        startForeground(1, notification)
        // конец отличия

        Toast.makeText(this, "Foreground Service created!", Toast.LENGTH_SHORT).show()
        running = true
        try {
            val filter = IntentFilter()
            filter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
            registerReceiver(MyServiceReceiver, filter)
        } catch (e: Exception) {
        }

        handler = Handler()
        runnable = Runnable {
            if (running) {
                printMsg("Foreground Service is still running $running")
                handler!!.postDelayed(runnable!!, 5000) // создает цикл
            } else {
                printMsg("Foreground Service exited")
            }
        }
        handler!!.postDelayed(runnable!!, 5000)
    }


    override fun onDestroy() {
        printMsg("Foreground Service stopped")
        try {
            running = false
            unregisterReceiver(MyServiceReceiver)
        } catch (e: java.lang.Exception) {
        }
        super.onDestroy()
    }

    fun printMsg(msg: String?) {
        val intent = Intent("printMsgFromService")
        intent.putExtra("msg", msg)
        sendBroadcast(intent)
//        Toast.makeText(getApplicationContext(), "ping", Toast.LENGTH_SHORT).show();
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY // означает, что сервис будет перезапущен в случае внезапной остановки, даже если приложение будет закрыто
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return true
    }
}