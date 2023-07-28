package ge.giosan777.matutu.mbasemobile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ge.giosan777.matutu.mbasemobile.navigator.Screen

@Preview
@Composable
fun prev() {
    WelcomeScreen(navController = rememberNavController())
}

@Composable
fun WelcomeScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .weight(0.7f)
                .background(Color.White)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(96.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(64.dp)
                        .offset(x = 16.dp, y = 16.dp),
                    painter = painterResource(id = R.drawable.m),
                    contentDescription = "image",
                )
                Image(
                    modifier = Modifier
                        .size(64.dp)
                        .padding(start = 8.dp)
                        .offset(x = 16.dp, y = 16.dp),
                    painter = painterResource(id = R.drawable.base), contentDescription = "image",
                )
            }
            Column(
                Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.policy_1),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(R.string.policy_2),
                    style = MaterialTheme.typography.bodyLarge
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(start = 16.dp, end = 16.dp)
                        .border(
                            border = BorderStroke(2.dp, Color.Black),
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Text(
                        text = stringResource(R.string.policy_text),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
        }

        val checked = remember {
            mutableStateOf(false)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(modifier = Modifier, checked = checked.value, onCheckedChange = {
                checked.value = !checked.value
            })
            Text(modifier = Modifier.clickable {
                checked.value = !checked.value
            }, text = stringResource(R.string.tanxmoba))
        }


        Row(
            Modifier
                .fillMaxSize()
                .weight(0.3f),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                Image(
//                    modifier = Modifier.size(128.dp),
//                    painter = painterResource(id = R.drawable.lets_get_started),
//                    contentDescription = "image",
//                )
                if (checked.value) {
                    Button(
                        modifier = Modifier,
                        onClick = {
                            navController.navigate(Screen.MBase.route){
                                popUpTo(route = Screen.WelcomeScreen.route){
                                    inclusive=true
                                }
                            }
                        },
                    ) {
                        Text(text = "Start", style = MaterialTheme.typography.displaySmall)
                    }
                } else {
                    Button(
                        modifier = Modifier,
                        onClick = {},
                        enabled = false
                    ) {
                        Text(text = "Start", style = MaterialTheme.typography.displaySmall)
                    }
                }

            }
        }
    }
}


//AndroidView(
//                factory = { context ->
//                    LayoutInflater.from(context).inflate(R.layout.welcome, null)
//                },
//                update = { view ->
//                    val btnStart = view.findViewById<Button>(R.id.btn_start)
//                    btnStart.setOnClickListener(View.OnClickListener {
//
//                    })
//                }
//            )
