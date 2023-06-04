package ge.giosan777.matutu.mbasemobile.screen_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopFun(onClick: (screen:String) -> Unit) {
    Row(
        modifier = Modifier
            .padding(top = 4.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = {
            onClick("mobile_base_org")
        }) {
            Text(text = "MOBILE BASE")
        }
        Button(onClick = {
            onClick("mobile_base_middle")
        }) {
            Text(text = "ORG BASE")
        }
    }
}