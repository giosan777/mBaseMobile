package ge.giosan777.matutu.mbasemobile.utils

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

private const val REQUEST_COD = 200
fun getPermission(context: Activity, permission: String): Boolean {
    return if (ContextCompat.checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(context, arrayOf(permission), REQUEST_COD)
        false
    } else {
        return true
    }

}