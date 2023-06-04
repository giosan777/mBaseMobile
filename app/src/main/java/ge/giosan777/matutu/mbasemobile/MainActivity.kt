package ge.giosan777.matutu.mbasemobile

import android.Manifest
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
import ge.giosan777.matutu.mbasemobile.Volley.getAllContactsFromServer
import ge.giosan777.matutu.mbasemobile.Volley.saveAllContactsFromPhoneToServer
import ge.giosan777.matutu.mbasemobile.database.getAllContactsFromPhoneMy
import ge.giosan777.matutu.mbasemobile.database.getAllPeopleFromLocalDb
import ge.giosan777.matutu.mbasemobile.database.saveAllContactsToLocalDb
import ge.giosan777.matutu.mbasemobile.sorting.contactSorting
import ge.giosan777.matutu.mbasemobile.utils.hasConnection


private const val REQUEST_COD = 1
private const val READ_CONTACTS = Manifest.permission.READ_CONTACTS

var APP_CONTEXT: MainActivity? = null

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(
            this, arrayOf(READ_CONTACTS), REQUEST_COD
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
        if (requestCode == REQUEST_COD) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (hasConnection(APP_CONTEXT!!)) {
                    standardStart()
                }

            } else {
                ActivityCompat.requestPermissions(
                    this@MainActivity, arrayOf(READ_CONTACTS), REQUEST_COD
                )

            }
        }
    }
}

fun standardStart() {

    getAllContactsFromServer(APP_CONTEXT!!) {
        val unsortingPhoneContacts = getAllContactsFromPhoneMy(APP_CONTEXT!!)
        val sortingPhoneContact = contactSorting(unsortingPhoneContacts)
        val setListSortingPhone=sortingPhoneContact.toMutableSet()
        val setListSortingServer=it.toMutableSet()
        val onlyPhoneList=setListSortingPhone.minus(setListSortingServer)
        setListSortingServer.addAll(setListSortingPhone)

        if (onlyPhoneList.isNotEmpty()) {
            saveAllContactsFromPhoneToServer(APP_CONTEXT!!, onlyPhoneList.toMutableList())
        }

        val localDbList= getAllPeopleFromLocalDb(APP_CONTEXT!!)
        val firstNewList=localDbList.minus(setListSortingServer)
        saveAllContactsToLocalDb(APP_CONTEXT!!, firstNewList.toMutableList())
    }

}







