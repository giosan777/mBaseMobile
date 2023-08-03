package ge.giosan777.matutu.mbasemobile.screen_components

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ge.giosan777.matutu.mbasemobile.APP_CONTEXT
import ge.giosan777.matutu.mbasemobile.R
import ge.giosan777.matutu.mbasemobile.mSettings
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TopAppBarMy() {
    TopAppBar(
        title = {
            Text("")
        },
        Modifier.background(MaterialTheme.colorScheme.primary),
        actions = {
            LocaleDropdownMenu()
            SwitchMy()
        },
        navigationIcon = {
            IconButton(onClick = {

            }) {
                Row(modifier = Modifier) {
                    Image(
                        painter = painterResource(id = R.drawable.m),
                        contentDescription = "logo image"
                    )
//                    Image(
//                        painter = painterResource(id = R.drawable.base),
//                        contentDescription = "logo image"
//                    )
                }
            }
        }
    )
}


@Composable
fun LocaleDropdownMenu() {
    var expanded = remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded.value = true }) {
            Icon(Icons.Filled.Language, contentDescription = "Показать меню")
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            Text(
                stringResource(id = R.string.ge), fontSize = 18.sp, modifier = Modifier
                    .padding(10.dp)
                    .clickable(onClick = {
                        expanded.value = false
                        val locale = Locale("ka")
                        Locale.setDefault(locale)
                        val resources = APP_CONTEXT.resources
                        val configuration = resources.configuration
                        configuration.locale = locale
                        resources.updateConfiguration(configuration, resources.displayMetrics)
                        mSettings
                            .edit()
                            .putString("language", "ka")
                            .apply()
                        (APP_CONTEXT as? Activity)?.recreate()
                    })
            )
            Text(
                stringResource(id = R.string.en), fontSize = 18.sp, modifier = Modifier
                    .padding(10.dp)
                    .clickable(onClick = {
                        expanded.value = false
                        val locale = Locale("en")
                        Locale.setDefault(locale)
                        val resources = APP_CONTEXT.resources
                        val configuration = resources.configuration
                        configuration.locale = locale
                        resources.updateConfiguration(configuration, resources.displayMetrics)
                        mSettings
                            .edit()
                            .putString("language", "en")
                            .apply()
                        (APP_CONTEXT as? Activity)?.recreate()

                    })
            )

        }
    }
}
