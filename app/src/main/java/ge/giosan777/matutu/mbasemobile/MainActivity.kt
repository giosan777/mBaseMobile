package ge.giosan777.matutu.mbasemobile

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ge.giosan777.matutu.mbasemobile.Volley.getAllContactsFromServer
import ge.giosan777.matutu.mbasemobile.Volley.saveAllContactsFromPhoneToServer
import ge.giosan777.matutu.mbasemobile.database.getAllContactsFromPhoneMy
import ge.giosan777.matutu.mbasemobile.database.getAllPeopleFromLocalDb
import ge.giosan777.matutu.mbasemobile.database.saveAllContactsToLocalDb
import ge.giosan777.matutu.mbasemobile.sorting.contactSorting
import ge.giosan777.matutu.mbasemobile.utils.hasConnection


private const val REQUEST_COD1 = 1
private const val REQUEST_COD2 = 2
private const val REQUEST_COD3 = 3
private const val READ_CONTACTS = Manifest.permission.READ_CONTACTS
private const val READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE
private const val READ_CALL_LOG = Manifest.permission.READ_CALL_LOG


var APP_CONTEXT: MainActivity? = null

class MainActivity : ComponentActivity() {

//    private var myBroadcastReceiver: Nothing? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        ActivityCompat.requestPermissions(
            this, arrayOf(READ_CONTACTS, READ_PHONE_STATE, READ_CALL_LOG), REQUEST_COD1
        )


        setContent {
            val navController = rememberNavController()
            APP_CONTEXT = this
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "background",
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.8f),
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, end = 15.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(3f),
                ) {
                    NavHost(navController = navController, startDestination = "ScreenMobileBase") {
                        composable(route = "ScreenMobileBase") {
                            ScreenMobileBase {
                                navController.navigate("ScreenMobileBaseOrg")
                            }
                        }
                        composable(route = "ScreenMobileBaseOrg") {
                            ScreenMobileBaseOrg {
                                navController.navigate("ScreenMobileBase") {
                                    popUpTo("ScreenMobileBase")
                                }
                            }
                        }

                    }

                }
            }

        }
    }


    class PhoneStateChangedReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            val i = Intent()
            i.setClassName(
                "ge.giosan777.matutu.mbasemobile",
                "ge.giosan777.matutu.mbasemobile.MainActivity2"
            )
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
                            Log.d("MyLog","dsadsa")



//            val phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
//            if (phoneState == TelephonyManager.EXTRA_STATE_RINGING) {
//                val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
//                Log.d("MyLog",incomingNumber.toString())
//                val call = Intent(Intent.ACTION_DIAL, Uri.parse("tel:8495-123-45-56"))
//                val call = Intent(Intent.ACTION_DIAL)
//                context.startActivity(call)

//                val intent = Intent(context, MainActivity2::class.java)
////                intent.putExtra(YourExtraKey, YourExtraValue)
//                context.startActivity(intent)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_COD1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    if (hasConnection(APP_CONTEXT!!)) {
                        standardStart()
                    }
            }

            REQUEST_COD2 -> {
                if (grantResults.isNotEmpty() && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                    if (hasConnection(APP_CONTEXT!!)) {
                        standardStart()
                    }
            }

            REQUEST_COD3 -> {
                if (grantResults.isNotEmpty() && grantResults[2] == PackageManager.PERMISSION_GRANTED)
                    if (hasConnection(APP_CONTEXT!!)) {
                        standardStart()
                    }
            }

            else -> {
                ActivityCompat.requestPermissions(
                    this@MainActivity, arrayOf(READ_CONTACTS), REQUEST_COD1
                )
            }
        }
    }

}


fun standardStart() {

    getAllContactsFromServer(APP_CONTEXT!!) {
        val unsortingPhoneContacts = getAllContactsFromPhoneMy(APP_CONTEXT!!)
        val sortingPhoneContact = contactSorting(unsortingPhoneContacts)
        val setListSortingPhone = sortingPhoneContact.toMutableSet()
        val setListSortingServer = it.toMutableSet()
        val onlyPhoneList = setListSortingPhone.minus(setListSortingServer)
        setListSortingServer.addAll(setListSortingPhone)

        if (onlyPhoneList.isNotEmpty()) {
            saveAllContactsFromPhoneToServer(APP_CONTEXT!!, onlyPhoneList.toMutableList())
        }

        val localDbList = getAllPeopleFromLocalDb(APP_CONTEXT!!)
        val firstNewList = localDbList.minus(setListSortingServer)
        saveAllContactsToLocalDb(APP_CONTEXT!!, firstNewList.toMutableList())
    }

}







