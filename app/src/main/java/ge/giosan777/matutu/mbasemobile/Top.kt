package ge.giosan777.matutu.mbasemobile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun TopFun() {
    Row(
        modifier = Modifier
            .padding(top = 4.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = { /*TODO*/ }) {
            Text(text = "baza")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "baza")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "baza")
        }
    }
}