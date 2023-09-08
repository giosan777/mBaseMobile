package ge.giosan777.matutu.mbasemobile

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ge.giosan777.matutu.mbasemobile.broadcast.Broadcast
import ge.giosan777.matutu.mbasemobile.navigator.Screen
import ge.giosan777.matutu.mbasemobile.navigator.SetUpNavGraph
import ge.giosan777.matutu.mbasemobile.screen_components.TopAppBarMy
import ge.giosan777.matutu.mbasemobile.ui.theme.MBaseTheme
import ge.giosan777.matutu.mbasemobile.utils.AlertDialogBattery
import ge.giosan777.matutu.mbasemobile.utils.AlertDialogCallLogPermission
import ge.giosan777.matutu.mbasemobile.utils.AlertDialogCallLogPermissionNeed
import ge.giosan777.matutu.mbasemobile.utils.AlertDialogCloseApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale


const val READ_CONTACTS_REQUEST_COD = 11
const val READ_PHONE_STATE_AND_CALL_LOG_REQUEST_COD = 22
const val NOTIFICATION_REQUEST_COD = 33

const val READ_CONTACTS = Manifest.permission.READ_CONTACTS
const val READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE
const val READ_CALL_LOG = Manifest.permission.READ_CALL_LOG
const val READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE //todo
const val POST_NOTIFICATIONS = Manifest.permission.POST_NOTIFICATIONS
const val APP_PREFERENCES = "mBaseSettings"

lateinit var APP_CONTEXT: MainActivity
lateinit var mSettings: SharedPreferences
lateinit var checkedState: MutableState<Boolean>
lateinit var overlayPermissionContract: ActivityResultLauncher<Intent>
lateinit var callLogShow: MutableState<Boolean>
val serviceTest = Broadcast()


class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private lateinit var appCloseDialog: MutableState<Boolean>
    private lateinit var appCloseDialogPermission: MutableState<Boolean>
    private lateinit var appPermissionNeed: MutableState<Boolean>
    private lateinit var appPermissionBattery: MutableState<Boolean>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        APP_CONTEXT = this@MainActivity
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        setLanguage()

        overlayPermissionContract = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (Settings.canDrawOverlays(this)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf(
                            POST_NOTIFICATIONS
                        ),
                        NOTIFICATION_REQUEST_COD
                    )
                }
                appPermissionNeed.value = true
                mSettings.edit().putBoolean("canDrawOverlays", true).apply()
            } else {
                mSettings.edit().putBoolean("canDrawOverlays", false).apply()
                checkedState.value = false
            }
        }



        setContent {
            appCloseDialog = remember {
                mutableStateOf(false)
            }
            appCloseDialogPermission = remember {
                mutableStateOf(false)
            }
            appPermissionNeed = remember {
                mutableStateOf(false)
            }
            callLogShow = remember {
                mutableStateOf(false)
            }
            appPermissionBattery = remember {
                mutableStateOf(false)
            }
            if (appCloseDialog.value) {
                AlertDialogCloseApplication() {
                    if (!it) {
                        ActivityCompat.requestPermissions(
                            this@MainActivity,
                            arrayOf(
                                READ_CONTACTS,
                            ),
                            READ_CONTACTS_REQUEST_COD
                        )
                    } else {
                        mSettings.edit().clear().apply()
                        this@MainActivity.finish()
                    }

                }
            }

            if (appCloseDialogPermission.value) {
                AlertDialogCallLogPermission() {
                    if (!it) {
                        ActivityCompat.requestPermissions(
                            this@MainActivity,
                            arrayOf(
                                READ_CONTACTS,
                            ),
                            READ_CONTACTS_REQUEST_COD
                        )
                    }
                }
            }
            if (appPermissionNeed.value) {
                AlertDialogCallLogPermissionNeed() {
                    ActivityCompat.requestPermissions(
                        APP_CONTEXT,
                        arrayOf(
                            READ_PHONE_STATE,
                            READ_CALL_LOG
                        ),
                        READ_PHONE_STATE_AND_CALL_LOG_REQUEST_COD
                    )
                }
            }
            if (appPermissionBattery.value) {
                AlertDialogBattery() {
                    if (it) {
                        val intent = Intent()
                        val pm: PowerManager =
                            getSystemService(Context.POWER_SERVICE) as PowerManager
                        if (pm.isIgnoringBatteryOptimizations(packageName)) {
                            intent.action = Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS
                        } else {
                            intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                            intent.data = Uri.parse("package:${packageName}")
                        }
                        startActivity(intent)
                    }
                }
            }

            MBaseTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        navController = rememberNavController()
                        TopAppBarMy(navController)
                        SetUpNavGraph(navController = navController)
                        if (!mSettings.contains("firstStart")) {
                            navController.navigate(route = Screen.WelcomeScreen.route)
                        }

                    }
                }
            }
        }
    }


    private fun showNotification(context: Context) {
        val channelId = "my_channel_id"
        val notificationId = 1111
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            "My Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.m)
            .setContentTitle("")
            .setContentText("")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity,
                    POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(notificationId, builder.build())
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
            READ_CONTACTS_REQUEST_COD -> {
                if (grantResults.isNotEmpty() && (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    Log.d("MyLog", "READ_CONTACTS_REQUEST_COD granted")
                    lifecycleScope.launch(Dispatchers.IO) {
                        showNotification(this@MainActivity)
                        firstStart()
                    }
                } else {
                    appCloseDialog.value = true
                }
            }

            READ_PHONE_STATE_AND_CALL_LOG_REQUEST_COD -> {
                if (grantResults.isNotEmpty() && grantResults.size >= 2 &&
                    (grantResults[0] == PackageManager.PERMISSION_GRANTED) &&
                    (grantResults[1] == PackageManager.PERMISSION_GRANTED)
                ) {
                    appPermissionBattery.value=true
                    callLogShow.value = true

                    val intentFilter = IntentFilter()
                    intentFilter.addAction("android.intent.action.PHONE_STATE")
                    APP_CONTEXT.registerReceiver(serviceTest, intentFilter)


//                    startForegroundService(Intent(this, CallsService::class.java))
//                    Log.d("MyLog", "READ_PHONE_STATE_AND_CALL_LOG_REQUEST_COD granted")
                } else {
                    appCloseDialogPermission.value = true
                }
            }

            else -> {
                Log.d("MyLog", "222222222222222")

//                ActivityCompat.requestPermissions(
//                    this@MainActivity,
//                    arrayOf(
//                        READ_CONTACTS,
////                        READ_PHONE_STATE,
////                        READ_CALL_LOG,
////                        READ_EXTERNAL_STORAGE,
////                        POST_NOTIFICATIONS
//                    ),
//                    REQUEST_COD1
//                )
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
        configuration.setLocale(locale)
//        resources.updateConfiguration(configuration, resources.displayMetrics)
        APP_CONTEXT.createConfigurationContext(configuration)

    }
}

