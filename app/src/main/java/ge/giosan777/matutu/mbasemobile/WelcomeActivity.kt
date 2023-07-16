package ge.giosan777.matutu.mbasemobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

lateinit var thisActivity: WelcomeActivity

class WelcomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        thisActivity = this
        installSplashScreen()
        setContent {
//            if (mSettings!!.contains("firstStart")) {
//                val intent = Intent(
//                    thisActivity,
//                    ge.giosan777.matutu.mbasemobile.MainActivity::class.java
//                )
//                thisActivity.startActivity(intent)
//                thisActivity.finish();
//            }else{
                StartScreen()
//            }
        }
    }

}


@Preview
@Composable
fun StartScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .weight(0.7f)
                .background(Color.White)
        ) {
            Image(
                modifier = Modifier
                    .size(64.dp)
                    .offset(x = 36.dp, y = 36.dp),
                painter = painterResource(id = R.drawable.m),
                contentDescription = "image",
            )
            Image(
                modifier = Modifier
                    .size(64.dp)
                    .padding(start = 8.dp)
                    .offset(x = 36.dp, y = 36.dp),
                painter = painterResource(id = R.drawable.base), contentDescription = "image",
            )
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
                Image(
                    modifier = Modifier.size(128.dp),
                    painter = painterResource(id = R.drawable.lets_get_started),
                    contentDescription = "image",
                )
                Button(
                    onClick = {
                        val intent = Intent(
                            thisActivity,
                            ge.giosan777.matutu.mbasemobile.MainActivity::class.java
                        )
                        thisActivity.startActivity(intent)
                        thisActivity.finish();

                    },
                ) {
                        Text(text = "Start", style = MaterialTheme.typography.displaySmall)
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