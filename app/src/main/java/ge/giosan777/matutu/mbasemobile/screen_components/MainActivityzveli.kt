package ge.giosan777.matutu.mbasemobile.screen_components//package ge.giosan777.matutu.mbasemobile
//
//import android.Manifest
//import android.content.Context
//import android.content.SharedPreferences
//import android.content.pm.PackageManager
//import android.os.Bundle
//import android.util.Log
//import android.widget.Toast
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.alpha
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.dp
//import androidx.core.app.ActivityCompat
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import ge.giosan777.matutu.mbasemobile.Volley.getAndSaveAllContacts
//import ge.giosan777.matutu.mbasemobile.utils.hasConnection
//
//private lateinit var prefs: SharedPreferences
//private const val REQUEST_COD = 1
//private const val READ_CONTACTS = Manifest.permission.READ_CONTACTS
//private var readContactPermission = false
//var APP_CONTEXT: MainActivity? = null
//
//class MainActivity : ComponentActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        ActivityCompat.requestPermissions(
//            this, arrayOf(READ_CONTACTS), REQUEST_COD
//        )
//        prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
//
//        setContent {
//            val navController = rememberNavController()
//            APP_CONTEXT = this
//
//
//            if (prefs.contains("read_contacts_permissions_granted") && prefs.getBoolean(
//                    "read_contacts_permissions_granted",
//                    false
//                )
//            ) {
//                if (hasConnection(this)) {
//                    getAndSaveAllContacts(this)
//                }
//                Toast.makeText(this, "UKVE MOCEMULIA", Toast.LENGTH_SHORT).show();
//            } else {
//                val editor = prefs.edit()
//                editor.putBoolean("read_contacts_permissions_granted", readContactPermission)
//                    .apply()
//                Toast.makeText(this, "EXLA MISCA", Toast.LENGTH_SHORT).show();
////                finish()
////                System.exit(0)
//            }
//
//            Image(
//                painter = painterResource(id = R.drawable.background),
//                contentDescription = "background",
//                modifier = Modifier
//                    .fillMaxSize()
//                    .alpha(0.8f),
//                contentScale = ContentScale.FillBounds
//            )
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(start = 15.dp, end = 15.dp)
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .weight(3f),
////                    verticalArrangement = Arrangement.SpaceAround,
////                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    NavHost(navController = navController, startDestination = "Middle2") {
//                        composable(route = "Middle2") {
//                            TopFun {
//                                Log.d("MyLog", "$it 11111")
//                                if (it == "mobile_base_org") {
//                                    navController.navigate("Middle3")
//                                }
//                            }
//
//                        }
//                        composable(route = "Middle3") {
//                            TopFun {
//                                Log.d("MyLog", "$it 2222")
//                                if (it == "mobile_base_middle") {
//                                    navController.navigate("Middle2")
//                                }
//                            }
//
//                        }
//
//                    }
////                    Middle(this@MainActivity)
//                }
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .weight(0.2f)
//                        .background(Color.Red),
//                    verticalArrangement = Arrangement.SpaceAround,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Reklama()
//                }
//            }
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
//        if (requestCode == REQUEST_COD) {
//            if (grantResults.isNotEmpty() && grantResults.get(0) == PackageManager.PERMISSION_GRANTED) {
//                val editor = prefs.edit()
//                editor.putBoolean("read_contacts_permissions_granted", true)
//                    .apply()
//                readContactPermission = true
//            } else {
//                ActivityCompat.requestPermissions(
//                    this@MainActivity, arrayOf(READ_CONTACTS), REQUEST_COD
//                )
//
//            }
//        }
//    }
//
//}
//
//
