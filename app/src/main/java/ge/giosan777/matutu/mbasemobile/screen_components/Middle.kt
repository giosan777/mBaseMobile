package ge.giosan777.matutu.mbasemobile.screen_components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ge.giosan777.matutu.mbasemobile.R
import ge.giosan777.matutu.mbasemobile.Volley.mobileBase.getNumberStartingWith
import ge.giosan777.matutu.mbasemobile.models.Person
import ge.giosan777.matutu.mbasemobile.ui.theme.meore
import ge.giosan777.matutu.mbasemobile.ui.theme.pirveli

@Composable
fun Middle(context: Context) {

    val personState = remember {
        mutableStateOf(mutableListOf<Person>())
    }
    val text = remember {
        mutableStateOf(TextFieldValue(""))
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
                    getNumberStartingWith(context,personState,text.value.text)
                },
                label = { Text(stringResource(R.string.enter_phone_number)) },
                modifier = Modifier
                    .height(60.dp)
                    .width(200.dp),
            )
//            Button(
//                onClick = {
//                    getOneContact(context, personState, "+995599068721")
//                },
//                modifier = Modifier.padding(start = 15.dp)
//            ) {
//                Text("getOne")
//            }
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

//
//@Composable
//fun ItemRow(person: Person) {
//
//    Row(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(3.dp)
//            .background(Color.White)
//
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(15.dp),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(text = person.firstName, fontStyle = FontStyle.Italic)
//            Text(text = person.lastName)
//            Text(text = person.phone)
//        }
//    }
//}
