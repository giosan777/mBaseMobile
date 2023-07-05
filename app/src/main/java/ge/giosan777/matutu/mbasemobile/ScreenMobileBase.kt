package ge.giosan777.matutu.mbasemobile

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ge.giosan777.matutu.mbasemobile.Volley.mobileBase.getNumberStartingWith
import ge.giosan777.matutu.mbasemobile.contacts.getAllJournal
import ge.giosan777.matutu.mbasemobile.models.Person
import ge.giosan777.matutu.mbasemobile.screen_components.JournalCard
import ge.giosan777.matutu.mbasemobile.screen_components.personCard
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Preview
@Composable
fun MyViewPreview() {
    ScreenMobileBase(onClick = {})
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun ScreenMobileBase(onClick: () -> Unit) {
    val personState = remember {
        mutableStateOf(mutableListOf<Person>())
    }
    val text = remember {
        mutableStateOf(TextFieldValue(""))
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
                        text = "MOBILE",
                        style = MaterialTheme.typography.displayMedium

                    )
                }
                Button(onClick = {
                    onClick()
                }, modifier = Modifier) {
                    Text(
                        text = "ORG BASE", style = MaterialTheme.typography.displayMedium
                    )
                }
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
                        TextField(
                            value = text.value,
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.text_field_mobile_icon),
                                    contentDescription = "text_field_org_icon"
                                )
                            },
                            onValueChange = { it ->
                                cardVisible.value = true
                                text.value = it
                                GlobalScope.launch(Dispatchers.IO) {
                                    getNumberStartingWith(
                                        APP_CONTEXT!!,
                                        personState,
                                        text.value.text
                                    )
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
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                        )
                        Text(
                            modifier = Modifier
                                .padding(end = 15.dp)
                                .clickable {
                                    cardVisible.value = false
                                    text.value = TextFieldValue("")
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
                Text(text = "DANAREKEBIS JURNALI", fontSize = 16.sp, fontStyle = FontStyle.Italic)

                LazyColumn {
                    GlobalScope.launch(Dispatchers.IO, start = CoroutineStart.UNDISPATCHED) {
                        val allJournal = getAllJournal()
                        itemsIndexed(allJournal) { index, item ->
                            JournalCard(journalItem = item)
                        }
                    }


                }


            }

        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2f)
                .background(Color.Red),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Reklama MOBILE")
        }
    }

}

