package ge.giosan777.matutu.mbasemobile

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
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
import androidx.core.content.ContextCompat
import ge.giosan777.matutu.mbasemobile.Volley.deleteAllContacts
import ge.giosan777.matutu.mbasemobile.Volley.getAndSaveAllContacts
import ge.giosan777.matutu.mbasemobile.utils.GetAllContacts
import ge.giosan777.matutu.mbasemobile.utils.getPermission


private const val READ_CONTACTS = Manifest.permission.READ_CONTACTS

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        getPermission(this, READ_CONTACTS)
        if (onRequestPermissions()) {
            Toast.makeText(this, "OK OK OK", Toast.LENGTH_LONG).show();
            deleteAllContacts(this)
            getAndSaveAllContacts(this)
        } else {
            Toast.makeText(this, "FALSE", Toast.LENGTH_LONG).show();
//            finish()
//            System.exit(0)
        }

        super.onCreate(savedInstanceState)
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


    private fun onRequestPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val array = GetAllContacts.getAll(this)
        for (a in array) {
            Log.d("MyLog", "Array NAME ${a._name} and Phone ${a._phone}")
        }
    }
}


