package ge.giosan777.matutu.mbasemobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import ge.giosan777.matutu.mbasemobile.ui.theme.MBaseTheme
import ge.giosan777.matutu.mbasemobile.utils.AlertDialogSample

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val phoneAndUser = remember {
                mutableStateOf("")
            }

            MBaseTheme {
                Surface(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AlertDialogSample()
//                    setFinishOnTouchOutside(true)
//                    val extras = intent.extras
//                    extras?.let {
//                        val number = extras.getString("number")
//                        val user = extras.getString("user")
//                        phoneAndUser.value = "$number  $user"
//                    }

                }
            }
        }
    }
}

