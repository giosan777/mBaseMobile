package ge.giosan777.matutu.mbasemobile.screen_components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ge.giosan777.matutu.mbasemobile.APP_CONTEXT
import ge.giosan777.matutu.mbasemobile.R
import ge.giosan777.matutu.mbasemobile.Volley.orgBase.saveNewOrganization
import ge.giosan777.matutu.mbasemobile.navigator.Screen
import ge.giosan777.matutu.mbasemobile.utils.AlertDialogInternetFromAddNewOrg
import ge.giosan777.matutu.mbasemobile.utils.hasConnection
import ge.giosan777.matutu.mbasemobile.utils.textTraslation
import ge.giosan777.matutu.mbasemobile.validation.organizationCategoryValidation
import ge.giosan777.matutu.mbasemobile.validation.organizationNameValidation
import ge.giosan777.matutu.mbasemobile.validation.organizationPhoneValidation
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.UUID


private val newOrganization =
    ge.giosan777.matutu.mbasemobile.models.Organization(0, "", "", "", "", "", "", "","","")

@Composable
fun AddCompanyCard(navController: NavController) {
    val companyName = remember {
        mutableStateOf("")
    }
    val companyPhone = remember {
        mutableStateOf("")
    }

    val companyWebSite = remember {
        mutableStateOf("")
    }
    val companyAddress = remember {
        mutableStateOf("")
    }
    val companyDescription = remember {
        mutableStateOf("")
    }
    val companyLanguages = remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current

    val color by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.tertiaryContainer
//        else MaterialTheme.colorScheme.primaryContainer,
    )
    val firstCardVisible = remember {
        mutableStateOf(true)
    }
    val secondCardVisible = remember {
        mutableStateOf(false)
    }

    val nameValidation = remember {
        mutableStateOf("")
    }
    val phoneValidation = remember {
        mutableStateOf("")
    }
    val categoryValidation = remember {
        mutableStateOf("")
    }
    val validationStatus = remember {
        mutableStateOf(false)
    }

    val selectedText = remember {
        mutableStateOf("")
    }




    if (firstCardVisible.value) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, top = 8.dp)
                .clickable { },
            elevation = CardDefaults.cardElevation(),
            shape = RoundedCornerShape(bottomStart = 32.dp, topEnd = 32.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    )
                    .background(color = color)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start

            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(value = companyName.value,
                        onValueChange = {
                            companyName.value = it
                            nameValidation.value = organizationNameValidation(companyName.value)
                            newOrganization.organizationName = "*${companyName.value.trim()}"
                        },
                        modifier = Modifier.padding(start = 16.dp, bottom = 5.dp, top = 5.dp),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                        label = {
                            Text(
                                stringResource(R.string.enter_org_name),
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        supportingText = {
                            if (nameValidation.value != "ok") {
                                Text(
                                    text = nameValidation.value, color = Color.Red
                                )
                            }
                        })
                    Image(
                        modifier = Modifier
                            .offset(y = 5.dp)
                            .width(12.dp)
                            .height(12.dp),
                        painter = painterResource(id = R.drawable.star_ico),
                        contentDescription = "available image"
                    )
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(value = companyPhone.value,
                        onValueChange = {
                            companyPhone.value = it
                            phoneValidation.value = organizationPhoneValidation(companyPhone.value)
                            newOrganization.phone = companyPhone.value
                        },
                        modifier = Modifier.padding(start = 16.dp, bottom = 5.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                        label = {
                            Text(
                                stringResource(R.string.enter_company_phone),
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        supportingText = {
                            if (phoneValidation.value != "ok") {
                                Text(
                                    text = phoneValidation.value, color = Color.Red
                                )
                            }
                        })
                    Image(
                        modifier = Modifier
                            .offset(y = 5.dp)
                            .width(12.dp)
                            .height(12.dp),
                        painter = painterResource(id = R.drawable.star_ico),
                        contentDescription = "available image"
                    )
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    categoryValidation.value = organizationCategoryValidation(selectedText.value)
                    MyContent(selectedText, color, categoryValidation)
                    val selectedTextTraslated = textTraslation(selectedText.value)
                    newOrganization.category = selectedTextTraslated

                    Image(
                        modifier = Modifier
                            .offset(y = 5.dp)
                            .width(12.dp)
                            .height(12.dp),
                        painter = painterResource(id = R.drawable.star_ico),
                        contentDescription = "available image",

                        )
                }
                OutlinedTextField(value = companyAddress.value,
                    onValueChange = {
                        companyAddress.value = it
                        newOrganization.address = companyAddress.value
                    },
                    modifier = Modifier.padding(start = 16.dp, bottom = 5.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }),
                    label = {
                        Text(
                            stringResource(R.string.enter_company_address),
                            style = MaterialTheme.typography.labelSmall
                        )
                    })
                OutlinedTextField(value = companyLanguages.value,
                    onValueChange = {
                        companyLanguages.value = it
                        newOrganization.languages = companyLanguages.value
                    },
                    modifier = Modifier.padding(start = 16.dp, bottom = 5.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }),
                    label = {
                        Text(
                            stringResource(R.string.enter_company_languages),
                            style = MaterialTheme.typography.labelSmall
                        )
                    })
                OutlinedTextField(value = companyWebSite.value,
                    onValueChange = {
                        companyWebSite.value = it
                        newOrganization.webSite = companyWebSite.value
                    },
                    modifier = Modifier.padding(start = 16.dp, bottom = 5.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }),
                    label = {
                        Text(
                            stringResource(R.string.enter_company_web_site),
                            style = MaterialTheme.typography.labelSmall
                        )
                    })


                OutlinedTextField(value = companyDescription.value,
                    onValueChange = {
                        companyDescription.value = it
                        newOrganization.description = companyDescription.value
                    },
                    modifier = Modifier.padding(start = 16.dp, bottom = 15.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }),
                    label = {
                        Text(
                            stringResource(R.string.enter_company_description),
                            style = MaterialTheme.typography.labelSmall
                        )
                    })
                if (nameValidation.value == "ok" && phoneValidation.value == "ok" && categoryValidation.value == "ok") {
                    validationStatus.value = true
                }
                RequestContentPermission(firstCardVisible, secondCardVisible, validationStatus)


            }

        }
    }
    if (secondCardVisible.value) {
        UploadComplate(secondCardVisible,navController)
    }


}


@Composable
fun RequestContentPermission(
    firstCardVisible: MutableState<Boolean>,
    secondCardVisible: MutableState<Boolean>,
    validatorsStatus: MutableState<Boolean>
) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val context = LocalContext.current

    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }
    var file: File
    val uploadProgress = remember {
        mutableStateOf(false)
    }
    val completeClicked = remember {
        mutableStateOf(false)
    }
    val imagePicker = remember {
        mutableStateOf(true)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }
    val uuid = UUID.randomUUID()



    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (imagePicker.value) {
                Button(onClick = {
                    launcher.launch("image/*")
                }, modifier = Modifier.padding(start = 16.dp, bottom = 10.dp)) {
                    Text(text = "Pick image")
                }
            }


            Spacer(modifier = Modifier.width(32.dp))



            imageUri?.let {
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)

                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, it)
                    bitmap.value = ImageDecoder.decodeBitmap(source)
                }
                file = File(getRealPathFromURI(imageUri!!, APP_CONTEXT) ?: "")
                if ((file.length() / 1000) > 1024) {
                    Text(
                        text = "Logo Max. size 1MB", color = Color.Red
                    )
                } else {
                    newOrganization.img = uuid.toString()
                    bitmap.value?.let { btm ->
                        Image(
                            bitmap = btm.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }
            }
        }

        if (!completeClicked.value) {
            if (hasConnection()) {
                Button(onClick = {
                    if (validatorsStatus.value) {
                        completeClicked.value = true
                        imagePicker.value = false
                        val saveOrg = listOf(newOrganization)
                        val result = GlobalScope.async {
                            saveNewOrganization(APP_CONTEXT, saveOrg)
                            imageUri?.let {
                                uploadImage(
                                    getRealPathFromURI(imageUri!!, APP_CONTEXT)!!, uuid.toString()
                                )
                            }
                            true
                        }
                        GlobalScope.launch {
                            if (result.await()) {
                                uploadProgress.value = true
                                delay(2000)
                                firstCardVisible.value = false
                                secondCardVisible.value = true

                            }
                        }
                    }
                }, modifier = Modifier.padding(start = 16.dp, bottom = 10.dp, end = 16.dp)) {
                    Text(text = "Complete")
                }
            } else {
                AlertDialogInternetFromAddNewOrg()
            }

        }

    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        if (uploadProgress.value) {
            CircularProgressIndicator()
        }
    }

}

val MEDIA_TYPE_JPG = "image/*".toMediaType()
fun uploadImage(imageUri: String, imageName: String) {
    val client = OkHttpClient()


    val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart(
        "file", imageName, File(imageUri).asRequestBody(MEDIA_TYPE_JPG)
    ).build()

    val request = Request.Builder().url("https://mbase.ge/").post(requestBody).build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
    }
}


fun getRealPathFromURI(uri: Uri, context: Context): String? {
    val returnCursor = context.contentResolver.query(uri, null, null, null, null)
    val nameIndex = returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    val sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE)
    returnCursor.moveToFirst()
    val name = returnCursor.getString(nameIndex)
    val size = returnCursor.getLong(sizeIndex).toString()
    val file = File(context.filesDir, name)
    try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        var read = 0
        val maxBufferSize = 1 * 1024 * 1024
        val bytesAvailable: Int = inputStream?.available() ?: 0
        //int bufferSize = 1024;
        val bufferSize = Math.min(bytesAvailable, maxBufferSize)
        val buffers = ByteArray(bufferSize)
        while (inputStream?.read(buffers).also {
                if (it != null) {
                    read = it
                }
            } != -1) {
            outputStream.write(buffers, 0, read)
        }
//        Log.e("MyLog", "Size " + file.length())
        inputStream?.close()
        outputStream.close()
//        Log.e("MyLog", "Path " + file.path)

    } catch (e: java.lang.Exception) {
        Log.e("Exception", e.message!!)
    }
    return file.path
}

@Composable
fun UploadComplate(secondCardVisible: MutableState<Boolean>,navController:NavController) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp, top = 8.dp)
            .clickable { },
        elevation = CardDefaults.cardElevation(),
        shape = RoundedCornerShape(bottomStart = 32.dp, topEnd = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 36.dp, bottom = 36.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "After moderation your organization will be added.",
                style = MaterialTheme.typography.labelSmall
            )
            Button(
                onClick = {
                    secondCardVisible.value = false
                    navController.navigate(Screen.MBaseOrg.route) {
                        popUpTo(route = Screen.WelcomeScreen.route) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier.padding(top = 24.dp)
            ) {
                Text(text = "Ok")
            }
        }

    }
}


// to create an Outlined Text Field
// Calling this function as content
// in the above function
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyContent(
    selectedText: MutableState<String>, color: Color, categoryValidation: MutableState<String>
) {

    val options = listOf(
        stringResource(R.string.category),
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
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {
        expanded = !expanded
    }) {
        OutlinedTextField(modifier = Modifier
            .menuAnchor()
            .background(color)
            .padding(start = 16.dp, bottom = 5.dp),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            label = { Text(stringResource(R.string.category)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            supportingText = {
                if (categoryValidation.value != "ok") {
                    Text(text = categoryValidation.value, color = Color.Red)
                }
            })
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = {
            expanded = false
        }) {
            options.forEach { selectionOption ->
                DropdownMenuItem(text = { Text(text = selectionOption) }, onClick = {
                    selectedText.value = selectionOption
                    selectedOptionText = selectionOption
                    expanded = false
                })
            }
        }
    }

}