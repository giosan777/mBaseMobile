package ge.giosan777.matutu.mbasemobile

import android.content.Context
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import ge.giosan777.matutu.mbasemobile.Volley.getAndSaveAllContacts
import ge.giosan777.matutu.mbasemobile.Volley.getOneContact
import ge.giosan777.matutu.mbasemobile.models.Person
import ge.giosan777.matutu.mbasemobile.ui.theme.meore
import ge.giosan777.matutu.mbasemobile.ui.theme.pirveli

@Composable
fun Middle(context: Context) {

    val personState = remember {
        mutableStateOf(mutableListOf<Person>())
    }
    val state = remember {
        mutableStateOf("")
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
            OutlinedTextField(
                value = "+995599068721",
                onValueChange = { println(it) },
                label = { Text(stringResource(R.string.enter_phone_number)) },
                modifier = Modifier
                    .height(60.dp)
                    .width(20.dp),
            )
            Row {
                Button(
                    onClick = {
                        getOneContact(context,personState,"+995599068721")
                    },
                    modifier = Modifier.padding(start = 15.dp)
                ) {
                    Text("getOne")
                }
                Button(
                    onClick = {
                        getAndSaveAllContacts(context)
                    },
                    modifier = Modifier.padding(start = 15.dp)
                ) {
                    Text("GetAllContacts")
                }
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
                    personState.value.getOrNull(1)?.let {
                        Text("  "+it.phone)
                    }
                    Text(state.value)

                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .background(Color.Red)
                        .padding(start = 5.dp)
                ) {
                    personState.value.getOrNull(0)?.let {
                        Text(" "+it.firstName + "  "+it.lastName)
                    }
                    personState.value.getOrNull(1)?.let {
                        Text(" "+it.firstName + "  "+it.lastName)
                    }
                    personState.value.getOrNull(2)?.let {
                        Text(" "+it.firstName + "  "+it.lastName)
                    }
                    personState.value.getOrNull(3)?.let {
                        Text(" "+it.firstName + "  "+it.lastName)
                    }
                    personState.value.getOrNull(4)?.let {
                        Text(" "+it.firstName + "  "+it.lastName)
                    }
                }
            }
        }



//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//            itemsIndexed(
//                myList
//            ) { _, person ->
//                ItemRow(person = person)
//            }
//        }
    }
}


@Composable
fun ItemRow(person: Person) {

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(3.dp)
            .background(Color.White)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = person.firstName, fontStyle = FontStyle.Italic)
            Text(text = person.lastName)
            Text(text = person.phone)
        }
    }
}