package ge.giosan777.matutu.mbasemobile.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

@Composable
fun DialogPermission(
    dialogVisible: MutableState<Boolean>,
    readContactPermission: MutableState<Boolean>,
    finish: ()-> Unit
) {
    AlertDialog(onDismissRequest = {
//        dialogPermission.value=true
    },
        confirmButton = {
            TextButton(onClick = {

            }) {
                Text(text = "Ok")
            }
        },
        dismissButton = {
            TextButton(onClick = {
//                if (!readContactPermission.value) {
//                    finish()
//                }
            }) {
                Text(text = "Cancel")
            }
        },
        title = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "TITLE")
            }
        }
    )

}