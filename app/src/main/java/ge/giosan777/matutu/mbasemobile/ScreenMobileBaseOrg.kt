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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ge.giosan777.matutu.mbasemobile.Volley.orgBase.findByOrganizationNameIsContaining
import ge.giosan777.matutu.mbasemobile.Volley.orgBase.getCategoryStartingNameWithOrg
import ge.giosan777.matutu.mbasemobile.Volley.orgBase.getStartingNameWithOrg
import ge.giosan777.matutu.mbasemobile.banner.Banner
import ge.giosan777.matutu.mbasemobile.models.Organization
import ge.giosan777.matutu.mbasemobile.navigator.Screen
import ge.giosan777.matutu.mbasemobile.screen_components.OrgCard
import ge.giosan777.matutu.mbasemobile.utils.textTraslation
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun PrevMobileBaseOrg() {
    ScreenMobileBaseOrg(navController = rememberNavController())
}

//@Preview
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun ScreenMobileBaseOrg(navController: NavController) {

    val orgState = remember {
        mutableStateOf(mutableListOf<Organization>())
    }
    val orgSearch = remember {
        mutableStateOf("")
    }
    val orgCategorySearch = remember {
        mutableStateOf("")
    }
    val cardVisible = remember {
        mutableStateOf(false)
    }

    var companyAddExpanded by remember { mutableStateOf(false) }
    val companyCategoryExpanded = remember { mutableStateOf(false) }


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
                    navController.navigate(route = Screen.MBase.route) {
                        popUpTo(route = Screen.MBaseOrg.route) {
                            inclusive = true
                        }
                    }
                }, modifier = Modifier) {
                    Text(
                        text = stringResource(id = R.string.phone),
                        style = MaterialTheme.typography.displayMedium
                    )
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(R.color.btn_disabled)),
                    modifier = Modifier,
                    shape = RoundedCornerShape(topStart = 32.dp, bottomEnd = 32.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.company),
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
                            value = orgSearch.value,
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.text_field_org_icon),
                                    contentDescription = "text_field_org_icon"
                                )
                            },
                            onValueChange = {
                                if (it.isNotEmpty() && it[0] == '*') {
                                    orgSearch.value = it
                                } else {
                                    cardVisible.value = true
                                    orgSearch.value = it
                                    if (orgSearch.value.length > 2) {
                                        GlobalScope.launch(Dispatchers.IO) {
                                            findByOrganizationNameIsContaining(
                                                orgState,
                                                orgSearch.value
                                            )
                                        }
                                    }else cardVisible.value=false
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
                                    orgSearch.value = ""
                                },
                            text = "Clear",
                            style = MaterialTheme.typography.labelSmall
                        )

                    }

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.clickable {
                            companyCategoryExpanded.value = !companyCategoryExpanded.value
                        },
                        text = stringResource(R.string.else_search_category),
                        style = MaterialTheme.typography.labelSmall,
                    )
                    OrgCategorySearch(
                        companyCategoryExpanded = companyCategoryExpanded.value,
                        onClick = {
                            companyCategoryExpanded.value = !companyCategoryExpanded.value
                        })


                    Text(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .clickable {
                                orgCategorySearch.value = ""
                                cardVisible.value = false
                            },
                        text = "Clear",
                        style = MaterialTheme.typography.labelSmall
                    )

                }
                Text(text = orgCategorySearch.value)
                if (companyCategoryExpanded.value) {
                    CategoryChoice(orgCategorySearch, companyCategoryExpanded)
                }

                if (orgCategorySearch.value.isNotEmpty()) {
                    val trasleted = textTraslation(orgCategorySearch.value)
                    cardVisible.value = true
                    getCategoryStartingNameWithOrg(
                        orgState,
                        trasleted
                    )
                }


                Button(
                    onClick = { companyAddExpanded = !companyAddExpanded },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = stringResource(R.string.add_organization))
                        OrgAddItemButton(
                            companyAddExpanded = companyAddExpanded,
                            onClick = { companyAddExpanded = !companyAddExpanded })
                    }
                }
                if (companyAddExpanded) {
                    navController.navigate(Screen.AddCompanyCard.route) {
                        popUpTo(route = Screen.AddCompanyCard.route) {
                            inclusive = true
                        }
                    }
                }

                LazyColumn {
                    itemsIndexed(orgState.value) { index, _ ->
                        if (orgState.value.isNotEmpty() && cardVisible.value && !companyAddExpanded) {
                            OrgCard(orgCard = orgState, rigi = index)
                        }
                    }
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
fun OrgAddItemButton(
    companyAddExpanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    IconButton(
        onClick = onClick,
        modifier = modifier.padding(start = 5.dp, end = 5.dp)
    ) {
        Icon(
            imageVector = if (companyAddExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(R.string.expand_button_content_description),
        )
    }

}

@Composable
fun OrgCategorySearch(
    companyCategoryExpanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    IconButton(
        onClick = onClick,
        modifier = modifier.padding(end = 5.dp)
    ) {
        Icon(
            imageVector = if (companyCategoryExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(R.string.expand_button_content_description),
        )
    }

}

@Composable
fun CategoryChoice(
    selectedText: MutableState<String>,
    companyCategoryExpanded: MutableState<Boolean>
) {

    val options = listOf(
        stringResource(R.string.category_bank),
        stringResource(R.string.category_taxi),
        stringResource(R.string.category_pizza),
        stringResource(R.string.category_shawerma),
        stringResource(R.string.category_totalizator),
        stringResource(R.string.category_restourant),
        stringResource(R.string.category_dazgveva),
        stringResource(R.string.category_online_sekhi),
        stringResource(R.string.category_uzravi_qoneba),
        stringResource(R.string.category_apotneka),
        stringResource(R.string.category_teqnikis_magazia),
        stringResource(R.string.category_sastumro),
        stringResource(R.string.category_silamazis_saloni),
        stringResource(R.string.category_sadgesascaulo_centri),
        stringResource(R.string.category_turistuli_compania),
        stringResource(R.string.category_internet_provider),
        stringResource(R.string.category_arasamtavrobo),
        stringResource(R.string.category_clinika),
        stringResource(R.string.category_saxelmcifo_dacesebuleba),
        stringResource(R.string.category_microsafinanso),
        stringResource(R.string.category_mshenebloba),
        stringResource(R.string.category_amanati),
        stringResource(R.string.category_comunaluri),
        stringResource(R.string.category_avto_gaqiraveba),
        stringResource(R.string.category_skola),
        stringResource(R.string.category_comp_momsaxureba),
        stringResource(R.string.category_ziza),
        stringResource(R.string.category_damlagebeli),
        stringResource(R.string.category_flaerebis_darigeba),
        stringResource(R.string.category_ofis_menegeri),
        stringResource(R.string.category_masajisti),
        stringResource(R.string.category_mzgoli),
        stringResource(R.string.category_diasaxlisi),
        stringResource(R.string.category_ojaxis_eqimi),
        stringResource(R.string.category_repetitori),
        stringResource(R.string.category_bugalteri),
        stringResource(R.string.category_molare),
        stringResource(R.string.category_gatboba),
        stringResource(R.string.category_dacva),
        stringResource(R.string.category_iuristi),
        stringResource(R.string.category_tarjimani),
        stringResource(R.string.category_tvirtis_gadazidva),
        stringResource(R.string.category_evakuatori),
        stringResource(R.string.category_sxva)
    )

    Card(modifier = Modifier.background(Color.White)) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            options.forEach { itString ->
                ClickableText(
                    modifier = Modifier,
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic,
                                color = Color.Black,
                                fontSize = 18.sp
                            )
                        ) {
                            append(itString)
                        }
                    }, onClick = {
                        selectedText.value = itString
                        companyCategoryExpanded.value = false
                    })
                Divider()
                Divider()
                Divider()
                Divider()
                Divider()
            }
        }
    }

}