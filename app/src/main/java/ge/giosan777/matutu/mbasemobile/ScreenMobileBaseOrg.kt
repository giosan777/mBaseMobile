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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import ge.giosan777.matutu.mbasemobile.Volley.orgBase.getStartingNameWithOrg
import ge.giosan777.matutu.mbasemobile.models.Organization
import ge.giosan777.matutu.mbasemobile.screen_components.addCompanyCard
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
        mutableStateOf("")
    }
    val cardVisible = remember {
        mutableStateOf(false)
    }

    var expanded by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, end = 15.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
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
                        OutlinedTextField(
                            value = text.value,
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.text_field_org_icon),
                                    contentDescription = "text_field_org_icon"
                                )
                            },
                            onValueChange = { it
                                ->
                                if (it.isNotEmpty()&&it[0] == '*') {
                                    text.value = it
                                }else{
                                    cardVisible.value = true
                                    text.value = it
                                    GlobalScope.launch(Dispatchers.IO) {
                                        getStartingNameWithOrg(APP_CONTEXT!!, orgState, text.value)
                                    }
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
                            textStyle = MaterialTheme.typography.labelSmall
                            )
                        Text(
                            modifier = Modifier
                                .padding(end = 15.dp)
                                .clickable {
                                    cardVisible.value = false
                                    text.value = ""
                                },
                            text = "Clear",
                            style = MaterialTheme.typography.labelSmall
                        )

                    }

                }

                Button(onClick = { expanded = !expanded },modifier=Modifier.padding(top = 8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = stringResource(R.string.daamate_organizacia))
                        OrgAddItemButton(expanded = expanded, onClick = { expanded = !expanded })
                    }
                }
                if (expanded) {
                    addCompanyCard(expanded)
                }

                LazyColumn {
                    itemsIndexed(orgState.value) { index, item ->
                        if (orgState.value.isNotEmpty() && cardVisible.value && !expanded) {
                            orgCard(orgCard = orgState, rigi = index)
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

@Composable
fun OrgAddItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    IconButton(
        onClick = onClick,
        modifier = modifier.padding(start = 5.dp, end = 5.dp)
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(R.string.expand_button_content_description),
        )
    }

}
