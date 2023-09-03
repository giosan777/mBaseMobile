package ge.giosan777.matutu.mbasemobile.screen_components

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ge.giosan777.matutu.mbasemobile.APP_CONTEXT
import ge.giosan777.matutu.mbasemobile.checkedState
import ge.giosan777.matutu.mbasemobile.mSettings
import ge.giosan777.matutu.mbasemobile.overlayPermissionContract


@Composable()
fun SwitchMy() {

    checkedState = remember {
        mutableStateOf(false)
    }

    if (mSettings.contains("canDrawOverlays") && mSettings.getBoolean(
            "canDrawOverlays",
            false
        )
    ) {
        checkedState.value = true

    }

    Switch(
        checked = checkedState.value,
        onCheckedChange = {

            checkedState.value = it
            checkedStateIsActive.value=it

            if (checkedState.value) {
                if (!Settings.canDrawOverlays(APP_CONTEXT)) {
                    val intent =
                        Intent(
                            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:${APP_CONTEXT.packageName}")
                        )
                    overlayPermissionContract.launch(intent)

                }


                mSettings.edit().putBoolean("canDrawOverlays", true)
                    .apply()
            } else if (!checkedState.value) {

//                val serviceIntent =
//                    Intent(APP_CONTEXT, CallsService::class.java)
                mSettings.edit().putBoolean("canDrawOverlays", false)
                    .apply()

//                APP_CONTEXT.unregisterReceiver(serviceTest)
//                APP_CONTEXT.stopService(serviceIntent)
            }
        },
        thumbContent = {
            Icon(
                imageVector = if (checkedState.value) Icons.Filled.Check else Icons.Filled.Close,
                contentDescription = null,
                modifier = Modifier.size(SwitchDefaults.IconSize)
            )
        },
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.Green,
            checkedIconColor = Color.DarkGray,
            uncheckedThumbColor = Color.Red,
            uncheckedIconColor = Color.LightGray,
        ),
    )
}