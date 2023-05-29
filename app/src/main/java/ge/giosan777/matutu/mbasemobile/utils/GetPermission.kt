package ge.giosan777.matutu.mbasemobile.utils

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ge.giosan777.matutu.mbasemobile.READ_CONTACTS_PERMISSION

private const val REQUEST_COD = 200
fun getPermission(context: Activity, permission: String): Boolean {
    ActivityCompat.requestPermissions(context, arrayOf(permission), REQUEST_COD)
    return if (ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        READ_CONTACTS_PERMISSION=true
        true
    } else {
        return false
    }

}