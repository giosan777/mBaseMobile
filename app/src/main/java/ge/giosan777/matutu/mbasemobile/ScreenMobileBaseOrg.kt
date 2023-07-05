package ge.giosan777.matutu.mbasemobile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import ge.giosan777.matutu.mbasemobile.Volley.orgBase.getStartingNameWithOrg
import ge.giosan777.matutu.mbasemobile.models.Organization
import ge.giosan777.matutu.mbasemobile.screen_components.orgCard
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Preview
@Composable
fun MyViewPreviewOrg() {
    ScreenMobileBaseOrg(onClick = {})
}

//@Preview
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun ScreenMobileBaseOrg(onClick: () -> Unit) {
    val navController = rememberNavController()
    val orgState = remember {
        mutableStateOf(mutableListOf<Organization>())
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
                Button(onClick = {
                    onClick()
                }, modifier = Modifier) {
                    Text(
                        text = "MOBILE", style = MaterialTheme.typography.displayMedium
                    )
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(R.color.btn_disabled)),
                    modifier = Modifier,
                    shape = RoundedCornerShape(topStart = 32.dp, bottomEnd = 32.dp)
                ) {
                    Text(
                        text = "ORG BASE",
                        style = MaterialTheme.typography.displayMedium
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
                                    painter = painterResource(R.drawable.text_field_org_icon),
                                    contentDescription = "text_field_org_icon"
                                )
                            },
                            onValueChange = { it ->
                                cardVisible.value = true
                                text.value = it
                                GlobalScope.launch(Dispatchers.IO) {
                                    getStartingNameWithOrg(APP_CONTEXT!!, orgState, text.value.text)
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            label = {
                                Text(
                                    stringResource(R.string.enter_org_name),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            modifier = Modifier
                                .height(60.dp)
                                .fillMaxWidth(),
                            placeholder = {
                                Text(
                                    stringResource(R.string.example_org),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },

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
                if (orgState.value.isNotEmpty() && cardVisible.value) {
                    orgCard(orgCard = orgState, rigi = 0)
                }

                ///////////////////////////////////
                if (orgState.value.size >= 2 && cardVisible.value) {
                    orgCard(orgCard = orgState, rigi = 1)

                }
                ///////////////////////////////////
                if (orgState.value.size >= 3 && cardVisible.value) {
                    orgCard(orgCard = orgState, rigi = 2)

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
