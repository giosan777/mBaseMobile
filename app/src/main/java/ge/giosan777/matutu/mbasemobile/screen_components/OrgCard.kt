package ge.giosan777.matutu.mbasemobile.screen_components

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import ge.giosan777.matutu.mbasemobile.APP_CONTEXT
import ge.giosan777.matutu.mbasemobile.R
import ge.giosan777.matutu.mbasemobile.models.Organization


@Composable
fun orgCard(orgCard: MutableState<MutableList<Organization>>, rigi: Int) {
    var isExpanded by remember {
           mutableStateOf(false)
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
                    if (orgCard.value.isNotEmpty()) {
                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = orgCard.value[rigi].phone,
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
                        text = orgCard.value[rigi].organizationName,
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
                    Button(elevation = ButtonDefaults.buttonElevation(),
                        onClick = {
                            val intent = Intent(Intent.ACTION_DIAL)
                            intent.data = Uri.parse("tel:${orgCard.value[rigi].phone}")
                            startActivity(APP_CONTEXT!!,intent, Bundle())
                        }) {
                        Image(
                            painterResource(id = R.drawable.call_ico),
                            contentDescription = "Call",
                            modifier = Modifier.width(30.dp)
                        )
                    }

                }
            }

        }
    }

}