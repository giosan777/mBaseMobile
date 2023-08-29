package ge.giosan777.matutu.mbasemobile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ge.giosan777.matutu.mbasemobile.Volley.mobileBase.getAllContactsFromServer
import ge.giosan777.matutu.mbasemobile.Volley.mobileBase.getNumberStartingWith
import ge.giosan777.matutu.mbasemobile.Volley.mobileBase.saveAllContactsFromPhoneToServer
import ge.giosan777.matutu.mbasemobile.Volley.orgBase.getAllContactsFromServerOrg
import ge.giosan777.matutu.mbasemobile.banner.Banner
import ge.giosan777.matutu.mbasemobile.contacts.getAllContactsFromPhoneMy
import ge.giosan777.matutu.mbasemobile.database.deleteAllContactsFromLocalDB
import ge.giosan777.matutu.mbasemobile.database.deleteAllOrganizationsFromLocalDBOrg
import ge.giosan777.matutu.mbasemobile.database.getAllJournalFromLocalDbJournal
import ge.giosan777.matutu.mbasemobile.database.saveAllContactsToLocalDb
import ge.giosan777.matutu.mbasemobile.database.saveAllContactsToLocalDbOrg
import ge.giosan777.matutu.mbasemobile.models.Person
import ge.giosan777.matutu.mbasemobile.navigator.Screen
import ge.giosan777.matutu.mbasemobile.screen_components.JournalCard
import ge.giosan777.matutu.mbasemobile.screen_components.SwitchMy
import ge.giosan777.matutu.mbasemobile.screen_components.personCard
import ge.giosan777.matutu.mbasemobile.sorting.contactSorting1
import ge.giosan777.matutu.mbasemobile.utils.AlertDialogInternet
import ge.giosan777.matutu.mbasemobile.utils.hasConnection
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.system.exitProcess


@Preview(showBackground = true)
@Composable
fun PrevMobileBase() {
    ScreenMobileBase(navController = rememberNavController())
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun ScreenMobileBase(navController: NavController) {
    IsFirstStart()


    val personState = remember {
        mutableStateOf(mutableListOf<Person>())
    }
    val enterText = remember {
        mutableStateOf("")
    }
    val cardVisible = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, end = 15.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f),
//                    verticalArrangement = Arrangement.SpaceAround,
//                    horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(R.color.btn_enabled)),
                    modifier = Modifier,
                    shape = RoundedCornerShape(bottomStart = 32.dp, topEnd = 32.dp)
                ) {
                    Text(
                        text = stringResource(R.string.phone),
                        style = MaterialTheme.typography.displayMedium

                    )
                }
                Button(onClick = {
                    navController.navigate(route = Screen.MBaseOrg.route)
                }, modifier = Modifier) {
                    Text(
                        text = stringResource(R.string.company),
                        style = MaterialTheme.typography.displayMedium
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(modifier= Modifier.weight(0.9f),verticalAlignment = Alignment.CenterVertically) {
                    SwitchMy()
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(R.string.Identify_call)
                    )
                }
                Icon(
                    modifier = Modifier
                        .weight(0.1f)
                        .clickable {
                            navController.navigate(route = Screen.VideoScreen.route)
                        }
                        .size(32.dp)
                        .padding(start = 4.dp),
                    painter = painterResource(R.drawable.question_icon),
                    contentDescription = "question icon"
                )

            }


            Column(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),

                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd,
                    ) {
                        OutlinedTextField(
                            value = enterText.value,
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.text_field_mobile_icon),
                                    contentDescription = "text_field_org_icon"
                                )
                            },
                            onValueChange = {
                                cardVisible.value = true
                                enterText.value = it
                                if (enterText.value.length > 5) {
                                    GlobalScope.launch(Dispatchers.IO) {
                                        getNumberStartingWith(
                                            APP_CONTEXT,
                                            personState,
                                            enterText.value
                                        )
                                    }
                                } else {
                                    cardVisible.value = false
                                }
                            },
                            label = {
                                Text(
                                    stringResource(R.string.enter_phone_number),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            modifier = Modifier
                                .height(60.dp)
                                .fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            textStyle = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            modifier = Modifier
                                .padding(end = 15.dp)
                                .clickable {
                                    cardVisible.value = false
                                    enterText.value = ""
                                },
                            text = "Clear",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }


                }
                if (personState.value.isNotEmpty() && cardVisible.value) {
                    personCard(personState = personState, rigi = 0)
                }

                ///////////////////////////////////
                if (personState.value.size >= 2 && cardVisible.value) {
                    personCard(personState = personState, rigi = 1)

                }
                ///////////////////////////////////
                if (personState.value.size >= 3 && cardVisible.value) {
                    personCard(personState = personState, rigi = 2)

                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.danarekebis_jurnali).uppercase(),
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.displayMedium
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    LazyColumn(modifier = Modifier.weight(0.95f)) {
                        if (checkedState.value && callLogShow.value) {
                            GlobalScope.launch(
                                Dispatchers.IO,
                                start = CoroutineStart.UNDISPATCHED
                            ) {
                                val allJournal =
                                    getAllJournalFromLocalDbJournal(APP_CONTEXT).reversed().take(50)
                                itemsIndexed(allJournal) { _, item ->
                                    JournalCard(journalItem = item)
                                }
                            }
                        }
                    }
                    Text(
                        modifier = Modifier
                            .weight(0.08f)
                            .clickable {
                                if (mSettings.contains("language") && mSettings.getString("language", "ka").equals("ka")) {
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://mbase.ge/policy")
                                    )
                                    startActivity(APP_CONTEXT, intent, Bundle())
                                }
                                else{
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://mbase.ge/policy_en")
                                    )
                                    startActivity(APP_CONTEXT, intent, Bundle())
                                }

                            },
                        text = stringResource(R.string.privacy_policy),
                        color = Color.Blue,
                        textDecoration = TextDecoration.Underline
                    )

                }


            }

        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Banner()
        }

    }


}

@Composable
fun IsFirstStart() {
    if (!mSettings.contains("firstStart")) {
        if (!hasConnection()) {
            AlertDialogInternet {
                APP_CONTEXT.finish()
                exitProcess(0)
            }
        }
        ActivityCompat.requestPermissions(
            APP_CONTEXT,
            arrayOf(
                READ_CONTACTS,
//                READ_PHONE_STATE,
//                READ_CALL_LOG,
//                READ_EXTERNAL_STORAGE,
//                POST_NOTIFICATIONS
            ),
            READ_CONTACTS_REQUEST_COD
        )
        mSettings.edit().putBoolean("firstStart", false).apply()


    } else {
        standardStart()
    }
}


private fun standardStart() {
    callLogShow.value = true
    getAllContactsFromServer(APP_CONTEXT) {
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

    getAllContactsFromServerOrg(APP_CONTEXT) {
        deleteAllOrganizationsFromLocalDBOrg()
        saveAllContactsToLocalDbOrg(it)
    }

}

fun firstStart() {


    val unsortingPhoneContacts = getAllContactsFromPhoneMy()
    val sortingPhoneContact = contactSorting1(unsortingPhoneContacts)
    val setListSortingPhone = sortingPhoneContact.toMutableSet().toMutableList()

    saveAllContactsFromPhoneToServer(APP_CONTEXT, setListSortingPhone)

    getAllContactsFromServer(APP_CONTEXT) {
        APP_CONTEXT.lifecycleScope.launch { }
        saveAllContactsToLocalDb(it)
    }

    getAllContactsFromServerOrg(APP_CONTEXT) {
        saveAllContactsToLocalDbOrg(it)
    }
}