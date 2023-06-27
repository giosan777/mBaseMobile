package ge.giosan777.matutu.mbasemobile.screen_components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ge.giosan777.matutu.mbasemobile.APP_CONTEXT
import ge.giosan777.matutu.mbasemobile.R
import ge.giosan777.matutu.mbasemobile.contacts.getAllContactsFromPhoneMy
import ge.giosan777.matutu.mbasemobile.models.Journal

@Composable
fun journalCard(journalItem: Journal) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var callType: Int=0
    val allPhoneContacts= getAllContactsFromPhoneMy(APP_CONTEXT!!)

    allPhoneContacts.forEach {
        val phoneTmp=it.phone.removePrefix("+995").replace("[^\\w+]".toRegex(), "")
        val journalTmp=journalItem.number.removePrefix("+995").replace("[^\\w+]".toRegex(), "")
        Log.d("MyLog","phoneTmp:${phoneTmp}   journalTmp:$journalTmp")
        if (phoneTmp == journalTmp){
            callType = when (journalItem.type) {
                1 -> R.drawable.phone_migebuli_ico
                2 -> R.drawable.phone_darekili_ico
                3 -> R.drawable.phone_darekili_ico
                4 -> R.drawable.phone_migebuli_ico
                5 -> R.drawable.phone_gamotovebuli_ico
                else ->R.drawable.error_phone_ico
            }
        }
        else{
            callType = when (journalItem.type) {
                1 -> R.drawable.phone_migebuli_red_ico
                2 -> R.drawable.phone_darekili_red_ico
                3 -> R.drawable.phone_darekili_red_ico
                4 -> R.drawable.phone_migebuli_red_ico
                5 -> R.drawable.phone_gamotovebuli_red_ico
                else ->R.drawable.error_phone_ico
            }
        }
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = CardDefaults.cardElevation(),
        shape = RoundedCornerShape(15.dp)
    ) {
        Row {
            Image(
                painter = painterResource(callType),
                contentDescription = "Photo",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .padding(5.dp)
                    .size(20.dp)
                    .clip(CircleShape),
            )
            Column {

            }


        }
    }
}