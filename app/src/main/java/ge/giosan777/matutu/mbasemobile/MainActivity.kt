package ge.giosan777.matutu.mbasemobile

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
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
import ge.giosan777.matutu.mbasemobile.Volley.mobileBase.getAllContactsFromServer
import ge.giosan777.matutu.mbasemobile.Volley.mobileBase.saveAllContactsFromPhoneToServer
import ge.giosan777.matutu.mbasemobile.contacts.getAllContactsFromPhoneMy
import ge.giosan777.matutu.mbasemobile.database.deleteAllContactsFromLocalDB
import ge.giosan777.matutu.mbasemobile.database.saveAllContactsToLocalDb
import ge.giosan777.matutu.mbasemobile.sorting.contactSorting1
import ge.giosan777.matutu.mbasemobile.utils.AlertDialogInternet
import ge.giosan777.matutu.mbasemobile.utils.AlertDialogPermissions
import ge.giosan777.matutu.mbasemobile.utils.hasConnection


private const val REQUEST_COD1 = 1
private const val READ_CONTACTS = Manifest.permission.READ_CONTACTS
private const val READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE
private const val READ_CALL_LOG = Manifest.permission.READ_CALL_LOG
const val APP_PREFERENCES = "mBaseSettings"


var APP_CONTEXT: MainActivity? = null

var mSettings: SharedPreferences? = null
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        APP_CONTEXT=this
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        setContent {
            if (!mSettings!!.contains("firstStart")) {
                AlertDialogPermissions {
                    if (it) {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(READ_CONTACTS, READ_PHONE_STATE, READ_CALL_LOG),
                            REQUEST_COD1
                        )
                        mSettings!!.edit().putBoolean("firstStart", false).apply()
                    } else {
                        this.finish()
                        System.exit(0)
                    }
                }
                if (!hasConnection(this)) {
                    AlertDialogInternet {
                        this.finish()
                        System.exit(0)
                    }
                }

            }else{
                standardStart()
            }

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


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_COD1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED
                ) {
                    firstStart()
//                    val uuid = UUID.randomUUID().toString()
//                    mSettings!!.edit().putString("uuid", uuid).apply()
//                    uuidSave(this, uuid)
//                    Log.d("MyLog", "onRequestPermissionsResult 1111111111111")
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

fun firstStart() {
    val unsortingPhoneContacts = getAllContactsFromPhoneMy(APP_CONTEXT!!)
    val sortingPhoneContact = contactSorting1(unsortingPhoneContacts)
    val setListSortingPhone = sortingPhoneContact.toMutableSet().toMutableList()

    saveAllContactsFromPhoneToServer(APP_CONTEXT!!, setListSortingPhone)
    getAllContactsFromServer(APP_CONTEXT!!){
        saveAllContactsToLocalDb(APP_CONTEXT!!, it)
    }
}

fun standardStart() {

    getAllContactsFromServer(APP_CONTEXT!!) {
//        val unsortingPhoneContacts = getAllContactsFromPhoneMy(APP_CONTEXT!!)
//        val sortingPhoneContact = contactSorting(unsortingPhoneContacts)
//        val setListSortingPhone = sortingPhoneContact.toMutableSet()
//        val setListSortingServer = it.toMutableSet()
//        val onlyPhoneList = setListSortingPhone.minus(setListSortingServer)
//        setListSortingServer.addAll(setListSortingPhone)
//        if (onlyPhoneList.isNotEmpty()) {
//            saveAllContactsFromPhoneToServer(APP_CONTEXT!!, onlyPhoneList.toMutableList())
//        }

        deleteAllContactsFromLocalDB(APP_CONTEXT!!)
        saveAllContactsToLocalDb(APP_CONTEXT!!, it.toMutableList())
    }


}







