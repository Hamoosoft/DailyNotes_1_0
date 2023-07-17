package com.soft.dailynotes.presentation.ui.notes.about

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.soft.dailynotes.R

@Composable
fun PersonalInfoSection(modifier: Modifier = Modifier) {
    val myImage = painterResource(id = R.drawable.my_image)
    val myImageDescription = stringResource(id = R.string.my_image_desc)
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.large_padding)),
            elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.medium_padding)),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(.5f))
        ) {
            Image(
                painter = myImage,
                contentDescription = myImageDescription,
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.min_GridSize))
                    .height(dimensionResource(id = R.dimen.min_height))
                    .clip(shape = MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = stringResource(id = R.string.full_name),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.medium_padding))
        )
        Text(
            text = stringResource(id = R.string.profission),
            textAlign = TextAlign.Center, style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.medium_padding))
        )
        Spacer(modifier = Modifier.weight(1f))
        ContactSection(text = R.string.phone, drawable = R.drawable.baseline_call)
        ContactSection(text = R.string.share, drawable = R.drawable.baseline_share)
        ContactSection(text = R.string.email, drawable = R.drawable.baseline_email)

    }


}

@Composable
fun ContactSection(
    modifier: Modifier = Modifier,
    @StringRes text: Int,
    @DrawableRes drawable: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painterResource(id = drawable),
                contentDescription = null
            )
        }
        Text(
            text = stringResource(id = text),
            style = MaterialTheme.typography.bodyLarge
        )

    }
}