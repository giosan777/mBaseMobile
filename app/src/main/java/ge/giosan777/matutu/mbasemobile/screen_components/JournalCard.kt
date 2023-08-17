package ge.giosan777.matutu.mbasemobile.screen_components

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import ge.giosan777.matutu.mbasemobile.APP_CONTEXT
import ge.giosan777.matutu.mbasemobile.R
import ge.giosan777.matutu.mbasemobile.Volley.mobileBase.getOneContactExcept
import ge.giosan777.matutu.mbasemobile.models.Journal
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone


@Composable
fun JournalCard(journalItem: Journal) {
    var firstNameFromServer by remember {
        mutableStateOf("")
    }
    var type: Int = 0
    var expanded by remember { mutableStateOf(false) }
    val color by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colorScheme.tertiaryContainer
        else MaterialTheme.colorScheme.primaryContainer,
        label = "",
    )

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
            .padding(5.dp)
            .clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(),
        shape = RoundedCornerShape(bottomStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                .background(color = color)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                val date = Date(journalItem.date)
                val sdf = SimpleDateFormat("HH:mm")
                sdf.timeZone = TimeZone.getTimeZone("GMT+4")

                val formattedDate: String = sdf.format(date)
                Text(modifier = Modifier.padding(start = 4.dp), text = formattedDate)

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
                produceState(initialValue = "") {
                    getOneContactExcept(numberRegex, APP_CONTEXT) {
                        firstNameFromServer = it
                    }
                    value = ""
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.fillMaxWidth().weight(0.9F),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center

                    ) {
                            Text(modifier = Modifier, text = numberRegex)
                            Text(
                                modifier = Modifier,
                                text = "- $firstNameFromServer"
                            )
                    }
                    Column (modifier = Modifier.fillMaxWidth().weight(0.1F)){
                        JournalItemButton(
                            expanded = expanded,
                            onClick = { expanded = !expanded }
                        )
                    }
                }


            }
            if (expanded) {
                ExtraJournalButtons(
                    journalItem, firstNameFromServer,
                    modifier = Modifier.padding(
                        start = dimensionResource(R.dimen.padding_small),
                        top = dimensionResource(R.dimen.padding_small),
                        end = dimensionResource(R.dimen.padding_small),
                        bottom = dimensionResource(R.dimen.padding_small)
                    )
                )
            }
        }
    }


}


@Composable
fun JournalItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(R.string.expand_button_content_description),
            tint = MaterialTheme.colorScheme.secondary
        )
    }

}

@Composable
fun ExtraJournalButtons(
    journalItem: Journal,
    firstName: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        Box() {
            Image(painter = painterResource(id = R.drawable.call_card), contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:${journalItem.number}")
                        startActivity(APP_CONTEXT, intent, Bundle())
                    })
        }

        Box() {
            Image(painter = painterResource(id = R.drawable.message_card),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        val toSms = "smsto:${journalItem.number}"
                        val messageText = ""
                        val sms = Intent(Intent.ACTION_SENDTO, Uri.parse(toSms))
                        sms.putExtra("sms_body", messageText)
                        startActivity(APP_CONTEXT, sms, Bundle())
                    })
        }

        Box() {
            Image(painter = painterResource(id = R.drawable.add_user_ico),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_INSERT)
                        intent.type = ContactsContract.Contacts.CONTENT_TYPE

                        intent.putExtra(ContactsContract.Intents.Insert.NAME, firstName)
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, journalItem.number)

                        startActivity(APP_CONTEXT, intent, Bundle())
                    })
        }
    }
}