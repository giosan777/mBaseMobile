package ge.giosan777.matutu.mbasemobile.screen_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ge.giosan777.matutu.mbasemobile.R
import ge.giosan777.matutu.mbasemobile.Volley.mobileBase.getOneContactExcept
import ge.giosan777.matutu.mbasemobile.models.Journal
import ge.giosan777.matutu.mbasemobile.models.Person


@Composable
fun JournalCard(journalItem: Journal) {
    var firstNameFromServer by remember {
        mutableStateOf("")
    }
    var type: Int = 0


    when (journalItem.type) {
        1 -> type = R.drawable.phone_migebuli_ico
        2 -> type = R.drawable.phone_darekili_ico
        3 -> type = R.drawable.phone_darekili_ico
        4 -> type = R.drawable.phone_migebuli_ico
        5 -> type = R.drawable.phone_gamotovebuli_ico
        else -> type = R.drawable.error_phone_ico
    }



    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = CardDefaults.cardElevation(),
        shape = RoundedCornerShape(15.dp),

        ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(type),
                contentDescription = "Photo",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .padding(5.dp)
                    .size(20.dp)
                    .clip(CircleShape),
            )
            val numberRegex =
                journalItem.number.removePrefix("+995").replace("[^\\w+]".toRegex(), "")
            getOneContactExcept(numberRegex) {
                firstNameFromServer = it
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(modifier = Modifier.padding(start = 5.dp), text = numberRegex)

                Row(modifier = Modifier.width(180.dp)) {
                    Text(modifier = Modifier.padding(end = 15.dp), text = "- $firstNameFromServer")
                }
            }


        }
    }
}


fun cardFun(journalItem: Journal, phoneContacts: MutableList<Person>): Journal {

    phoneContacts.forEach {
        val phoneTmp = it.phone.removePrefix("+995").replace("[^\\w+]".toRegex(), "")
        val journalTmp = journalItem.number.removePrefix("+995").replace("[^\\w+]".toRegex(), "")

        if (phoneTmp == journalTmp) {
            when (journalItem.type) {
                1 -> journalItem.type = R.drawable.phone_migebuli_ico
                2 -> journalItem.type = R.drawable.phone_darekili_ico
                3 -> journalItem.type = R.drawable.phone_darekili_ico
                4 -> journalItem.type = R.drawable.phone_migebuli_ico
                5 -> journalItem.type = R.drawable.phone_gamotovebuli_ico
                else -> journalItem.type = R.drawable.error_phone_ico
            }
        } else {
            when (journalItem.type) {
                1 -> journalItem.type = R.drawable.phone_migebuli_red_ico
                2 -> journalItem.type = R.drawable.phone_darekili_red_ico
                3 -> journalItem.type = R.drawable.phone_darekili_red_ico
                4 -> journalItem.type = R.drawable.phone_migebuli_red_ico
                5 -> journalItem.type = R.drawable.phone_gamotovebuli_red_ico
                else -> journalItem.type = R.drawable.error_phone_ico
            }
        }
    }

    return journalItem
}