package ge.giosan777.matutu.mbasemobile

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ge.giosan777.matutu.mbasemobile.Volley.getNumberStartingWith
import ge.giosan777.matutu.mbasemobile.database.getAllPeopleWithPhone
import ge.giosan777.matutu.mbasemobile.models.Person
import ge.giosan777.matutu.mbasemobile.ui.theme.meore
import ge.giosan777.matutu.mbasemobile.ui.theme.pirveli
import ge.giosan777.matutu.mbasemobile.utils.hasConnection


//@Preview
@Composable
fun ScreenMobileBase(onClick: () -> Unit) {
    val personState = remember {
        mutableStateOf(mutableListOf<Person>())
    }
    val text = remember {
        mutableStateOf(TextFieldValue(""))
    }

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
                Button(onClick = {}) {
                    Text(text = "MOBILE BASE")
                }
                Button(onClick = {
                    onClick()
                }) {
                    Text(text = "ORG BASE")
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
                    TextField(
                        value = text.value,
                        onValueChange = { it ->
                            text.value = it
                            if (hasConnection(APP_CONTEXT!!)) {
                                getNumberStartingWith(APP_CONTEXT!!, personState, text.value.text)
                            } else {
                                getAllPeopleWithPhone(APP_CONTEXT!!, personState, text.value.text)
                            }
                        },
                        label = { Text(stringResource(R.string.enter_phone_number)) },
                        modifier = Modifier
                            .height(60.dp)
                            .width(200.dp),
                    )

                    Button(onClick = {
//                            val call = Intent(Intent.ACTION_DIAL, Uri.parse("tel:8495-123-45-56"))
//                            APP_CONTEXT?.startActivity(call)
//                            Log.d("MyLog","dach")

                        val intent = Intent(APP_CONTEXT, MainActivity2::class.java)
//                        intent.putExtra(YourExtraKey, YourExtraValue)
                        APP_CONTEXT!!.startActivity(intent)


                    }) {
                        Text(text = "start")
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(meore),
                    elevation = CardDefaults.cardElevation(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(pirveli)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .background(Color.Green)
                        ) {
                            personState.value.getOrNull(0)?.let {
                                Text("  " + it.phone)
                            }
                            personState.value.getOrNull(1)?.let {
                                Text("  " + it.phone)
                            }
                            personState.value.getOrNull(2)?.let {
                                Text("  " + it.phone)
                            }
                            personState.value.getOrNull(3)?.let {
                                Text("  " + it.phone)
                            }
                            personState.value.getOrNull(4)?.let {
                                Text("  " + it.phone)
                            }

                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .background(Color.Red)
                                .padding(start = 5.dp)
                        ) {
                            personState.value.getOrNull(0)?.let {
                                Text(" " + it.firstName + "  " + it.lastName)
                            }
                            personState.value.getOrNull(1)?.let {
                                Text(" " + it.firstName + "  " + it.lastName)
                            }
                            personState.value.getOrNull(2)?.let {
                                Text(" " + it.firstName + "  " + it.lastName)
                            }
                            personState.value.getOrNull(3)?.let {
                                Text(" " + it.firstName + "  " + it.lastName)
                            }
                            personState.value.getOrNull(4)?.let {
                                Text(" " + it.firstName + "  " + it.lastName)
                            }
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
