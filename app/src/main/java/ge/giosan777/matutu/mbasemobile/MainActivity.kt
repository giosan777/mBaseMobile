package ge.giosan777.matutu.mbasemobile

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ge.giosan777.matutu.mbasemobile.Volley.getAndSaveAllContacts
import ge.giosan777.matutu.mbasemobile.utils.getPermission
import ge.giosan777.matutu.mbasemobile.utils.hasConnection

var READ_CONTACTS_PERMISSION = false
private const val READ_CONTACTS = Manifest.permission.READ_CONTACTS
private lateinit var prefs: SharedPreferences

class MainActivity : ComponentActivity() {
    override fun onResume() {
        super.onResume()

    }


    override fun onPause() {
        super.onPause()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)

        if (prefs.contains("read_contacts_permissions_granted") && prefs.getBoolean(
                "read_contacts_permissions_granted",
                false
            )
        ) {
            if (hasConnection(this)) {
                getAndSaveAllContacts(this)
            }
            Toast.makeText(this, "UKVE MOCEMULIA", Toast.LENGTH_SHORT).show();
        } else {
            getPermission(this, READ_CONTACTS)
            val editor = prefs.edit()
            editor.putBoolean("read_contacts_permissions_granted", READ_CONTACTS_PERMISSION)
                .apply()
            Toast.makeText(this, "EXLA MISCA", Toast.LENGTH_SHORT).show();
//            finish()
//            System.exit(0)
        }

        setContent {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "background",
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.8f),
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, end = 15.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(3f),
//                    verticalArrangement = Arrangement.SpaceAround,
//                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TopFun()
                    Middle(this@MainActivity)
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.2f)
                        .background(Color.Red),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Reklama()
                }
            }
        }
    }



}


