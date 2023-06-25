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
import androidx.navigation.compose.rememberNavController
import ge.giosan777.matutu.mbasemobile.Volley.orgBase.getNumberStartingWithOrg
import ge.giosan777.matutu.mbasemobile.models.Organization

//@Preview
@Composable
fun ScreenMobileBaseOrg(onClick: () -> Unit) {
    val navController = rememberNavController()
    val orgState = remember {
        mutableStateOf(mutableListOf<Organization>())
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
                Button(onClick = {
                    onClick()
                }, modifier = Modifier.width(140.dp)) {
                    Text(text = "MOBILE BASE")
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(R.color.citeli)),
                    modifier = Modifier.width(140.dp)
                ) {
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
                                painter = painterResource(R.drawable.text_field_org_icon),
                                contentDescription = "text_field_org_icon"
                            )
                        },
                        onValueChange = { it ->
                            text.value = it
                            getNumberStartingWithOrg(APP_CONTEXT!!, orgState, text.value.text)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        label = {
                            Text(
                                stringResource(R.string.enter_org_name),
                                fontSize = 12.sp
                            )
                        },
                        modifier = Modifier
                            .height(60.dp)
                            .fillMaxWidth(),
                        placeholder = {
                            Text(
                                stringResource(R.string.example_org),
                                fontSize = 14.sp
                            )
                        },

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
                                if (orgState.value.isNotEmpty()) {
                                    Text(text = orgState.value[0].orgName, maxLines = 1)
                                    if (orgState.value.size >= 2) {
                                        Text(text = orgState.value[1].orgName, maxLines = 1)
                                        if (orgState.value.size >= 3) {
                                            Text(
                                                text = orgState.value[2].orgName,
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
            Text(text = "Reklama ORG")
        }
    }
}   
