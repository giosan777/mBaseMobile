package ge.giosan777.matutu.mbasemobile

import android.util.Log
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ge.giosan777.matutu.mbasemobile.Volley.mobileBase.getNumberStartingWith
import ge.giosan777.matutu.mbasemobile.contacts.getAllJournal
import ge.giosan777.matutu.mbasemobile.database.getAllPeopleWithPhone
import ge.giosan777.matutu.mbasemobile.models.Person
import ge.giosan777.matutu.mbasemobile.screen_components.journalCard
import ge.giosan777.matutu.mbasemobile.screen_components.personCard
import ge.giosan777.matutu.mbasemobile.utils.hasConnection

@Preview
@Composable
fun MyViewPreview() {
    ScreenMobileBase(onClick = {})
}

@Composable
fun ScreenMobileBase(onClick: () -> Unit) {
    val personState = remember {
        mutableStateOf(mutableListOf<Person>())
    }
    val text = remember {
        mutableStateOf(TextFieldValue("599"))
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
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(R.color.citeli)),
                    modifier = Modifier.width(140.dp)
                ) {
                    Text(text = "MOBILE BASE")
                }
                Button(onClick = {
                    onClick()
                }, modifier = Modifier.width(140.dp)) {
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
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.text_field_mobile_icon),
                                contentDescription = "text_field_org_icon"
                            )
                        },
                        onValueChange = { it ->
                            text.value = it
                            if (hasConnection(APP_CONTEXT!!)) {
                                getNumberStartingWith(APP_CONTEXT!!, personState, text.value.text)
                                Log.d("MyLog", personState.toString())
                            } else {
                                getAllPeopleWithPhone(APP_CONTEXT!!, personState, text.value.text)
                            }
                        },
                        label = {
                            Text(
                                stringResource(R.string.enter_phone_number),
                                fontSize = 12.sp
                            )
                        },

                        modifier = Modifier
                            .height(60.dp)
                            .fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )

                }
                if (personState.value.isNotEmpty()) {
                    personCard(personState = personState, rigi = 0)
                }

                ///////////////////////////////////
                if (personState.value.size >= 2) {
                    personCard(personState = personState, rigi = 1)

                }
                ///////////////////////////////////
                if (personState.value.size >= 3) {
                    personCard(personState = personState, rigi = 2)

                }
                getAllJournal(APP_CONTEXT!!).forEach {
                    journalCard(it)
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
