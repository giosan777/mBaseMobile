package ge.giosan777.matutu.mbasemobile.screen_components

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ge.giosan777.matutu.mbasemobile.APP_CONTEXT
import ge.giosan777.matutu.mbasemobile.R
import ge.giosan777.matutu.mbasemobile.mSettings
import ge.giosan777.matutu.mbasemobile.navigator.Screen
import ge.giosan777.matutu.mbasemobile.other_permissions.isShowOnLockScreenPermissionEnable
import java.util.Locale


lateinit var checkedStateIsActive:MutableState<Boolean>

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarMy(navController: NavController) {

    val isXiaomi = remember {
        mutableStateOf(false)
    }
    checkedStateIsActive = remember {
        mutableStateOf(false)
    }

    if (Build.MANUFACTURER.equals("Xiaomi", true) && checkedStateIsActive.value) {
        isXiaomi.value = true
        if (isShowOnLockScreenPermissionEnable()) {
            isXiaomi.value = false
            Toast.makeText(APP_CONTEXT, "Permission is already granted!!", Toast.LENGTH_SHORT)
                .show()
        } else {
            isXiaomi.value = true
        }
    }
    

    TopAppBar(
        title = {
            Text("")
        },
        Modifier.background(MaterialTheme.colorScheme.primary),
        actions = {
            if (isXiaomi.value) {
                Text(
                    "Xiaomi Permissions",
                    color = Color.Red,
                    modifier = Modifier.clickable {

                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts("package", APP_CONTEXT.getPackageName(), null)
                        intent.data = uri
                        APP_CONTEXT.startActivity(intent)
//                        val intent = Intent("miui.intent.action.APP_PERM_EDITOR")
//                        intent.setClassName(
//                            "com.miui.securitycenter",
//                            "com.miui.permcenter.permissions.PermissionsEditorActivity"
//                        )
//                        intent.putExtra("extra_pkgname", APP_CONTEXT.packageName)
//                        APP_CONTEXT.startActivity(intent)
                    }
                )
                Icon(
                    modifier = Modifier
                        .clickable {
                            navController.navigate(route = Screen.VideoScreenXiaomi.route)
                            Log.d("MyLog","sadsadsads")
                        }
                        .size(32.dp)
                        .padding(start = 4.dp, end = 4.dp),
                    painter = painterResource(R.drawable.question_icon),
                    contentDescription = "question icon"
                )
            }
            LocaleDropdownMenu()
        },
        navigationIcon = {
            IconButton(onClick = {

            }) {
                Row(modifier = Modifier) {
                    Image(
                        painter = painterResource(id = R.drawable.m),
                        contentDescription = "logo image"
                    )
//                    Image(
//                        painter = painterResource(id = R.drawable.base),
//                        contentDescription = "logo image"
//                    )
                }
            }
        }
    )
}


@Composable
fun LocaleDropdownMenu() {
    val expanded = remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded.value = true }) {
            Icon(Icons.Filled.Language, contentDescription = "Показать меню")
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            Text(
                stringResource(id = R.string.ge), fontSize = 18.sp, modifier = Modifier
                    .padding(10.dp)
                    .clickable(onClick = {
                        expanded.value = false
                        val locale = Locale("ka")
                        Locale.setDefault(locale)
                        val resources = APP_CONTEXT.resources
                        val configuration = resources.configuration
                        configuration.locale = locale
                        resources.updateConfiguration(configuration, resources.displayMetrics)
                        mSettings
                            .edit()
                            .putString("language", "ka")
                            .apply()
                        (APP_CONTEXT as? Activity)?.recreate()
                    })
            )
            Text(
                stringResource(id = R.string.en), fontSize = 18.sp, modifier = Modifier
                    .padding(10.dp)
                    .clickable(onClick = {
                        expanded.value = false
                        val locale = Locale("en")
                        Locale.setDefault(locale)
                        val resources = APP_CONTEXT.resources
                        val configuration = resources.configuration
                        configuration.locale = locale
                        resources.updateConfiguration(configuration, resources.displayMetrics)
                        mSettings
                            .edit()
                            .putString("language", "en")
                            .apply()
                        (APP_CONTEXT as? Activity)?.recreate()

                    })
            )

        }
    }
}
