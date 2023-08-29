package ge.giosan777.matutu.mbasemobile

import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class DialogActivity : ComponentActivity() {
    private lateinit var windowManager: WindowManager
    private lateinit var dialogView: View
    private lateinit var dialog: AlertDialog
    private lateinit var phoneAndUser: MutableState<String>
    private var bus: EventBus = EventBus.getDefault()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            phoneAndUser = remember {
                mutableStateOf("")
            }
            setFinishOnTouchOutside(false)
            intent.extras?.getString("user")?.let {
                phoneAndUser.value = it

                newDialog(phoneAndUser.value)
            }
        }
    }


    @Subscribe
    fun messageAvailable(user: String) {
        Log.d("MyLog", "movida $user")
        if (this::windowManager.isInitialized) {
            windowManager.removeView(dialogView)
        }
        dialog.dismiss()
        newDialog(user)
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
        bus.register(DialogActivity@ this);
        val filter = IntentFilter("ACTION_DAKETVA_ACTIVITY")
        registerReceiver(myReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        bus.unregister(DialogActivity@ this);
        if (this::windowManager.isInitialized) {
            windowManager.removeView(dialogView)
        }
        dialog.dismiss()
        unregisterReceiver(myReceiver);
    }


    fun newDialog(userName: String) {
        val builder = AlertDialog.Builder(this, R.style.CustomDialog)
        val customDialogView: View = layoutInflater.inflate(
            R.layout.dialog_custom_layout,
            null
        )
        customDialogView.minimumWidth = 200
        customDialogView.minimumHeight = 100



        builder.setView(customDialogView)
        val btnOk =
            customDialogView.findViewById<ImageButton>(R.id.btn_ok)
        val txtUser =
            customDialogView.findViewById<TextView>(R.id.txt_user)
        txtUser.text = userName

        dialog = builder.create()

        dialog.window?.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);


        dialog.setOnCancelListener(DialogInterface.OnCancelListener {
            this.finish()
        })

        dialog.show()

        btnOk.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
            this.finish()
        })
    }

}


