package ge.giosan777.matutu.mbasemobile

import android.Manifest
import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ge.giosan777.matutu.mbasemobile.navigator.Screen
import ge.giosan777.matutu.mbasemobile.navigator.SetUpNavGraph
import ge.giosan777.matutu.mbasemobile.screen_components.TopAppBarMy
import ge.giosan777.matutu.mbasemobile.service.CallsService
import ge.giosan777.matutu.mbasemobile.ui.theme.MBaseTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale


const val REQUEST_COD1 = 1
const val READ_CONTACTS = Manifest.permission.READ_CONTACTS
const val READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE
const val READ_CALL_LOG = Manifest.permission.READ_CALL_LOG
const val READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
const val APP_PREFERENCES = "mBaseSettings"

lateinit var APP_CONTEXT: MainActivity
lateinit var mSettings: SharedPreferences
lateinit var checkedState: MutableState<Boolean>
lateinit var overlayPermissionContract: ActivityResultLauncher<Intent>


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        APP_CONTEXT = this@MainActivity
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
//        val intent = Intent()
//        intent.action = Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS
//        startActivity(intent)


        setLanguage()




        overlayPermissionContract = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (Settings.canDrawOverlays(this)) {
                startForegroundService(Intent(this, CallsService::class.java))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf(
                            POST_NOTIFICATIONS
                        ),
                        222
                    )

                }
                mSettings.edit().putBoolean("canDrawOverlays", true).apply()
                val intent = Intent()
                val pm : PowerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
                if (pm.isIgnoringBatteryOptimizations(APP_CONTEXT.packageName)) {
                    intent.action = Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS
                } else {
                    intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                    intent.data = Uri.parse("package:${APP_CONTEXT.packageName}")
                }
                APP_CONTEXT.startActivity(intent)
                Log.d("MyLog", "ARIIIIIIIIIIIIIIIIIIS")
            } else {
                mSettings.edit().putBoolean("canDrawOverlays", false).apply()
                Log.d("MyLog", "araaaaaaaaaaaaaaaaaaaaaaaaaaa")
                checkedState.value = false
            }
        }


        setContent {

            MBaseTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        TopAppBarMy()
                        navController = rememberNavController()
                        SetUpNavGraph(navController = navController)
                        if (!mSettings.contains("firstStart")) {
                            navController.navigate(route = Screen.WelcomeScreen.route)
                        }
                    }
                }
            }
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_COD1 -> {
                if (grantResults.isNotEmpty() && (grantResults.size >= 3) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    && (grantResults[1] == PackageManager.PERMISSION_GRANTED)
                    && (grantResults[2] == PackageManager.PERMISSION_GRANTED)
                ) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        firstStart()
                    }
                }
            }

            else -> {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(READ_CONTACTS, READ_PHONE_STATE, READ_CALL_LOG),
                    REQUEST_COD1
                )
            }
        }
    }


}


fun setLanguage() {
    if (mSettings.contains("language") && mSettings.getString("language", "ka").equals("ka")) {
        println(mSettings.getString("language", "ka"))
        val locale = Locale("ka")
        Locale.setDefault(locale)
        val resources = APP_CONTEXT.resources
        val configuration = resources.configuration
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}


