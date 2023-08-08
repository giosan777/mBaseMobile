package ge.giosan777.matutu.mbasemobile.screen_components

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import ge.giosan777.matutu.mbasemobile.APP_CONTEXT
import ge.giosan777.matutu.mbasemobile.R
import ge.giosan777.matutu.mbasemobile.models.Organization
import ge.giosan777.matutu.mbasemobile.utils.copyToClipboard


@Composable
fun OrgCard(orgCard: MutableState<MutableList<Organization>>, rigi: Int) {
    var expanded by remember { mutableStateOf(false) }
    val color by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colorScheme.tertiaryContainer
        else MaterialTheme.colorScheme.primaryContainer,
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(),
        shape = RoundedCornerShape(bottomStart = 32.dp, topEnd = 32.dp)
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

            Row {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        if (!orgCard.value[rigi].img.isNullOrEmpty()) {
                            AsyncImage(
                                model = "https://mbase.ge/files/${orgCard.value[rigi].img}",
                                contentDescription = "Organization logo",
                                modifier = Modifier
                                    .size(64.dp)
                                    .padding(start = 5.dp, top = 5.dp)
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.nologo),
                                contentDescription = "Photo",
                                contentScale = ContentScale.FillHeight,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .size(64.dp)
                                    .clip(CircleShape)
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
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



                        Box(modifier = Modifier.padding(end = 25.dp, top = 5.dp)) {
                            Image(painter = painterResource(id = R.drawable.call_card),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clickable {
                                        val intent = Intent(Intent.ACTION_DIAL)
                                        intent.data = Uri.parse("tel:${orgCard.value[rigi].phone}")
                                        ContextCompat.startActivity(APP_CONTEXT, intent, Bundle())
                                    })
                        }

                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${orgCard.value[rigi].category.uppercase()}: ${orgCard.value[rigi].organizationName}",
                            style = MaterialTheme.typography.bodyLarge,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(start = 10.dp),
                        )
                        OrgItemButton(expanded = expanded, onClick = { expanded = !expanded })
                    }
                    Divider(Modifier.padding(8.dp))
                    if (expanded) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Address: ${orgCard.value[rigi].address}",
                                style = MaterialTheme.typography.labelSmall,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(start = 10.dp).weight(0.7f,true),
                                maxLines = 2
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.google_map_icon),
                                contentDescription = "map_ico",
                                Modifier
                                    .padding(end = 16.dp)
                                    .clickable {
                                        val address = orgCard.value[rigi].address
                                        val uri = Uri.parse("geo:0,0?q=$address")
                                        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
//                                        mapIntent.setPackage("com.google.android.apps.maps")
                                        startActivity(APP_CONTEXT, mapIntent, Bundle())
                                    }
                            )
                        }
                        Divider(Modifier.padding(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "WebSite: ${orgCard.value[rigi].webSite}",
                                style = MaterialTheme.typography.labelSmall,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .padding(start = 10.dp).weight(0.7f,true),
                                maxLines = 2
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.copy_ico),
                                contentDescription = "phone_ico",
                                Modifier
                                    .padding(end = 16.dp)
                                    .clickable {
                                        copyToClipboard(
                                            APP_CONTEXT,
                                            orgCard.value[rigi].webSite ?: ""
                                        )
                                        Toast
                                            .makeText(
                                                APP_CONTEXT,
                                                "WebSite Copied",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                    }
                            )
                        }
                        Divider(Modifier.padding(8.dp))
                        Text(
                            text = "Description: ${orgCard.value[rigi].description}",
                            style = MaterialTheme.typography.labelSmall,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
                            maxLines = 7
                        )

                    }
                }

            }
        }
    }
}


@Composable
fun OrgItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    IconButton(
        onClick = onClick,
        modifier = modifier.padding(5.dp)
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(R.string.expand_button_content_description),
            tint = MaterialTheme.colorScheme.secondary
        )
    }

}

