package ge.giosan777.matutu.mbasemobile

import android.Manifest
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
import ge.giosan777.matutu.mbasemobile.database.deleteAllContactsFromLocalDB
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
        Log.d("MyLog", "unsortingPhoneContacts size ${unsortingPhoneContacts.size}")

        val sortingPhoneContact = contactSorting(unsortingPhoneContacts)
        Log.d("MyLog", "sortingPhoneContact size ${sortingPhoneContact.size}")

        val setListSortingPhone = sortingPhoneContact.toMutableSet()
        Log.d("MyLog", "setListSortingPhone size ${setListSortingPhone.size}")

        val setListSortingServer = it.toMutableSet()
        Log.d("MyLog", "setListSortingServer size ${setListSortingServer.size}")


        val onlyPhoneList = setListSortingPhone.minus(setListSortingServer)
        Log.d("MyLog", "onlyPhoneList size ${onlyPhoneList.size}")
        setListSortingServer.addAll(setListSortingPhone)
        Log.d("MyLog", "setListSortingServer size ${setListSortingServer.size}")

        if (onlyPhoneList.isNotEmpty()) {
            saveAllContactsFromPhoneToServer(APP_CONTEXT!!, onlyPhoneList.toMutableList())
        }

        deleteAllContactsFromLocalDB(APP_CONTEXT!!)
        saveAllContactsToLocalDb(APP_CONTEXT!!, setListSortingServer.toMutableList())


        val localDbList = getAllPeopleFromLocalDb(APP_CONTEXT!!)
        Log.d("MyLog", "localDbList size ${localDbList.size}")
    }


}







