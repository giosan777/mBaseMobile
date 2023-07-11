package ge.giosan777.matutu.mbasemobile

import android.R
import android.content.DialogInterface
import android.graphics.PixelFormat
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            val phoneAndUser = remember {
                mutableStateOf("")
            }

            setFinishOnTouchOutside(true)
            intent.extras?.getString("user")?.let {
                phoneAndUser.value = it
//                showOverlayDialog(phoneAndUser.value)
                newDialog(phoneAndUser.value)
            }


            intent.extras?.getBoolean("endCall")?.let {
                if (it) {
//                    dialog.dismiss()
                }
            }
            intent.extras?.getBoolean("endCall")?.let {
                if (it) {
//                    dialog.dismiss()
                }
            }


        }


    }


    fun newDialog(userName: String) {
        val builder = AlertDialog.Builder(this)
        val customDialogView: View = layoutInflater.inflate(
            ge.giosan777.matutu.mbasemobile.R.layout.dialog_custom_layout,
            null
        )
        builder.setView(customDialogView)
        val btnOk =
            customDialogView.findViewById<Button>(ge.giosan777.matutu.mbasemobile.R.id.btn_ok)
        val txtUser =
            customDialogView.findViewById<TextView>(ge.giosan777.matutu.mbasemobile.R.id.txt_user)
        txtUser.text = userName

         val dialog = builder.create()
        val dialogRootView = dialog.window!!.decorView.findViewById<View>(R.id.content)
        dialogRootView.minimumWidth = 300 // Ширина в пикселях
        dialogRootView.minimumHeight = 200 // Высота в пикселях
        dialog.window?.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);

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
        builder.setMessage("Пример диалогового окна поверх звонилки");
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
            this.finish()
        });

        val dialog: AlertDialog = builder.create();
        dialog.window?.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        dialog.show()
    }


}
