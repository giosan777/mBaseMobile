package ge.giosan777.matutu.mbasemobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ge.giosan777.matutu.mbasemobile.Volley.mobileBase.getNumberStartingWith
import ge.giosan777.matutu.mbasemobile.database.getAllPeopleWithPhone
import ge.giosan777.matutu.mbasemobile.models.Person
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

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    elevation = CardDefaults.cardElevation(),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Row {
                        Image(
                            painter = painterResource(R.drawable.person_shabl),
                            contentDescription = "Photo",
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier
                                .padding(5.dp)
                                .size(64.dp)
                                .clip(CircleShape)
                        )
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.smartphone_icon),
                                    contentDescription = "phone_ico",
                                    Modifier.padding(5.dp)
                                )
                                Text(
                                    modifier = Modifier.padding(start = 10.dp),
                                    text = text.value.text,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                            Column(modifier = Modifier.padding(start = 5.dp)) {
                                if (personState.value.isNotEmpty()) {
                                    Text(text = personState.value[0].firstName, maxLines = 1)
                                    if (personState.value.size >= 2) {
                                        Text(text = personState.value[1].firstName, maxLines = 1)
                                        if (personState.value.size >= 3) {
                                            Text(
                                                text = personState.value[2].firstName,
                                                maxLines = 1
                                            )
                                        }
                                    }
                                }
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
