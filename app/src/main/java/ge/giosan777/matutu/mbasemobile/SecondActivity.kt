package ge.giosan777.matutu.mbasemobile

import android.content.DialogInterface
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val phoneAndUser = remember {
                mutableStateOf("")
            }



            setFinishOnTouchOutside(true)
            intent.extras?.getString("user")?.let {
                phoneAndUser.value = it
                println("chairtooo " + this@SecondActivity)
//                showOverlayDialog(phoneAndUser.value)
                newDialog(phoneAndUser.value)
                finish()
            }
        }
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            if (intent.getBooleanExtra("endCall", false)) {
                this.finish()
            } else
                if (intent.getBooleanExtra("startCall", false)) {
                    val user = intent.getStringExtra("user")
                    newDialog(user!!)
                }
        }
    }


    fun newDialog(userName: String) {
        val builder = AlertDialog.Builder(this,R.style.CustomDialog)
        val customDialogView: View = layoutInflater.inflate(
            ge.giosan777.matutu.mbasemobile.R.layout.dialog_custom_layout,
            null
        )
        customDialogView.minimumWidth=200
        customDialogView.minimumHeight=100


        builder.setView(customDialogView)
        val btnOk =
            customDialogView.findViewById<ImageButton>(R.id.btn_ok)
        val txtUser =
            customDialogView.findViewById<TextView>(R.id.txt_user)
        txtUser.text = userName

        val dialog = builder.create()

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


    private fun showOverlayDialog(userName: String) {
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        );

        // Создание и настройка вашего диалогового окна
        val builder = AlertDialog.Builder(this);
        builder.setTitle("Rekavs $userName");
        builder.setMessage("Пример диалогов ого окна поверх звонилки");
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
            this.finish()
        });

        val dialog: AlertDialog = builder.create();
        dialog.window?.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        dialog.show()
    }


}
