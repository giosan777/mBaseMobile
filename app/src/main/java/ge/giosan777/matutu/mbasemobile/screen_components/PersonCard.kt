package ge.giosan777.matutu.mbasemobile.screen_components

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Snackbar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import ge.giosan777.matutu.mbasemobile.APP_CONTEXT
import ge.giosan777.matutu.mbasemobile.R
import ge.giosan777.matutu.mbasemobile.models.Person

@Preview
@Composable
fun MyViewPreview() {
    val personState = remember {
        mutableStateOf(mutableListOf<Person>())
    }
    personCard(personState, 1)
}


@Composable
fun personCard(personState: MutableState<MutableList<Person>>, rigi: Int) {
    var isExpanded by remember {
           mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = CardDefaults.cardElevation(),
        shape = RoundedCornerShape(bottomStart = 32.dp, topEnd = 32.dp)
    ) {
        Row {
            Image(
                painter = painterResource(R.drawable.person_shabl),
                contentDescription = "Photo",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .padding(5.dp)
                    .size(64.dp)
                    .clip(CircleShape)
            )
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.smartphone_icon),
                        contentDescription = "phone_ico",
                        Modifier.padding(5.dp)
                    )
                    if (personState.value.isNotEmpty()) {
                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = personState.value[rigi].phone,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = personState.value[rigi].firstName,
                        fontSize = 16.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                isExpanded = !isExpanded
                            },
                        maxLines = if (isExpanded)2 else 1,
                    )
                    Spacer(Modifier.width(16.dp))
                    Box() {
                        Image(painter = painterResource(id = R.drawable.call_card), contentDescription = null,
                            modifier = Modifier
                                .size(48.dp)
                                .clickable {
                                    val intent = Intent(Intent.ACTION_DIAL)
                                    intent.data = Uri.parse("tel:${personState.value[rigi].phone}")
                                    startActivity(APP_CONTEXT!!, intent, Bundle())
                                })
                    }
                    Spacer(Modifier.width(16.dp))
                    Box() {
                        Image(painter = painterResource(id = R.drawable.message_card), contentDescription = null,
                            modifier = Modifier
                                .size(48.dp)
                                .clickable {
                                    val toSms = "smsto:${personState.value[rigi].phone}"
                                    val messageText = ""
                                    val sms = Intent(Intent.ACTION_SENDTO, Uri.parse(toSms))
                                    sms.putExtra("sms_body", messageText)
                                    startActivity(APP_CONTEXT, sms, Bundle())
                                })
                    }

                }
            }

        }
    }

}

