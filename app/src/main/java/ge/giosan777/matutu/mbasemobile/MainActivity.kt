@file:OptIn(DelicateCoroutinesApi::class)

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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ge.giosan777.matutu.mbasemobile.Volley.mobileBase.getAllContactsFromServer
import ge.giosan777.matutu.mbasemobile.Volley.mobileBase.saveAllContactsFromPhoneToServer
import ge.giosan777.matutu.mbasemobile.Volley.orgBase.getAllContactsFromServerOrg
import ge.giosan777.matutu.mbasemobile.contacts.getAllContactsFromPhoneMy
import ge.giosan777.matutu.mbasemobile.database.deleteAllContactsFromLocalDB
import ge.giosan777.matutu.mbasemobile.database.deleteAllOrganizationsFromLocalDBOrg
import ge.giosan777.matutu.mbasemobile.database.saveAllContactsToLocalDb
import ge.giosan777.matutu.mbasemobile.database.saveAllContactsToLocalDbOrg
import ge.giosan777.matutu.mbasemobile.sorting.contactSorting1
import ge.giosan777.matutu.mbasemobile.ui.theme.MBaseTheme
import ge.giosan777.matutu.mbasemobile.utils.AlertDialogInternet
import ge.giosan777.matutu.mbasemobile.utils.AlertDialogPermissions
import ge.giosan777.matutu.mbasemobile.utils.hasConnection
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val REQUEST_COD1 = 1
private const val READ_CONTACTS = Manifest.permission.READ_CONTACTS
private const val READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE
private const val READ_CALL_LOG = Manifest.permission.READ_CALL_LOG
const val APP_PREFERENCES = "mBaseSettings"


var APP_CONTEXT: MainActivity? = null


var mSettings: SharedPreferences? = null

@OptIn(DelicateCoroutinesApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        APP_CONTEXT = this
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);




        setContent {
            MBaseTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {


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

                    } else {
                        standardStart()
                    }

                    val navController = rememberNavController()
                    APP_CONTEXT = this

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
                            NavHost(
                                navController = navController,
                                startDestination = "ScreenMobileBase"
                            ) {
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
                if (grantResults.isNotEmpty() && grantResults.size >= 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED
                ) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        firstStart()
                    }

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

    private fun firstStart() {

        val unsortingPhoneContacts = getAllContactsFromPhoneMy()
        val sortingPhoneContact = contactSorting1(unsortingPhoneContacts)
        val setListSortingPhone = sortingPhoneContact.toMutableSet().toMutableList()

        saveAllContactsFromPhoneToServer(APP_CONTEXT!!, setListSortingPhone)

        getAllContactsFromServer(APP_CONTEXT!!) {
            lifecycleScope.launch { }
            saveAllContactsToLocalDb(it)
        }

        getAllContactsFromServerOrg(APP_CONTEXT!!) {
            saveAllContactsToLocalDbOrg(it)
        }
    }

    private fun standardStart() {

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

            deleteAllContactsFromLocalDB()
            saveAllContactsToLocalDb(it.toMutableList())
        }

        getAllContactsFromServerOrg(APP_CONTEXT!!) {
            deleteAllOrganizationsFromLocalDBOrg()
            saveAllContactsToLocalDbOrg(it)
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MBaseTopAppBar(modifier: Modifier = Modifier) {
        CenterAlignedTopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.image_size))
                            .padding(dimensionResource(R.dimen.padding_small)),
                        painter = painterResource(R.drawable.call_ico),
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.displayMedium
                    )
                }
            },
            modifier = modifier
        )
    }

}













