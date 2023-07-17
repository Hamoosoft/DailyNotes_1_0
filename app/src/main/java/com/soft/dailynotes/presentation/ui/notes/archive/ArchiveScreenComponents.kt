import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.soft.dailynotes.R
import com.soft.dailynotes.domain.models.Archive
import com.soft.dailynotes.presentation.ui.notes.home.getDate
import com.soft.dailynotes.presentation.ui.utils.Utils

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArchiveNotesItem(
    modifier: Modifier = Modifier,
    notes: Archive,
    context: Context,
    applyNotificationDialog: (String,Int) -> Unit,
    onLongClick: () -> Unit,
    deleteNoteFromArchiveEvent: (Archive) -> Unit,
    restoreNoteToNotesRepository: (Archive) -> Unit,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.small_padding))
            .heightIn(
                min = dimensionResource(
                    id = R.dimen.extra_min_height
                )
            )
            .combinedClickable(onLongClick = { onLongClick() }, onClick = { onClick(notes.noteId) })
            .clip(
                RoundedCornerShape(dimensionResource(id = R.dimen.medium_padding))
            ),
        elevation = dimensionResource(id = R.dimen.small_padding),
        backgroundColor = if (notes.isDark) Utils.colorsDark[notes.color] else Utils.colors[notes.color]
    ) {
        Column {
            Text(
                text = notes.title, maxLines = 1, overflow = TextOverflow.Clip,
                style = MaterialTheme.typography.titleMedium, modifier = modifier.padding(
                    dimensionResource(id = R.dimen.medium_padding)
                )
            )
            CompositionLocalProvider(values = arrayOf(LocalContentAlpha provides ContentAlpha.disabled)) {
                Text(
                    text = notes.description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify, overflow = TextOverflow.Ellipsis,
                    modifier = modifier.padding(
                        dimensionResource(id = R.dimen.medium_padding)
                    )
                )
            }
            Spacer(modifier = modifier.padding(dimensionResource(id = R.dimen.dp1_padding)))
            CompositionLocalProvider(values = arrayOf(LocalContentAlpha provides ContentAlpha.disabled)) {
                Text(
                    text = getDate(notes.date),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify, overflow = TextOverflow.Ellipsis,
                    modifier = modifier
                        .padding(
                            dimensionResource(id = R.dimen.medium_padding)
                        )
                        .align(alignment = Alignment.End)
                )
            }
            Divider(modifier = modifier.height(dimensionResource(id = R.dimen.small_padding)))
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { deleteNoteFromArchiveEvent(notes) }
                ) {
                    Icon(
                        painterResource(id = R.drawable.baseline_delete_forever),
                        contentDescription = stringResource(id = R.string.delete)
                    )
                }
                IconButton(
                    onClick = {
                        shareNotes(
                            notes.title,
                            notes.description,
                            getDate(notes.date),
                            context = context
                        )
                    }
                ) {
                    Icon(
                        painterResource(id = R.drawable.baseline_share),
                        contentDescription = stringResource(id = R.string.share)
                    )
                }
                IconButton(
                    onClick = { restoreNoteToNotesRepository(notes) }
                ) {
                    Icon(
                        painterResource(id = R.drawable.baseline_restore),
                        contentDescription = stringResource(id = R.string.restore)
                    )
                }
                if (notes.schedule.isNotBlank()) {
                    IconButton(
                        onClick = { applyNotificationDialog(notes.title,notes.noteId) }
                    ) {
                        Icon(
                            painterResource(id = R.drawable.baseline_notifications_active),
                            contentDescription = stringResource(id = R.string.notification)
                        )
                    }
                } else {
                    IconButton(
                        onClick = { applyNotificationDialog(notes.title,notes.noteId) }
                    ) {
                        Icon(
                            painterResource(id = R.drawable.baseline_notifications_none),
                            contentDescription = stringResource(id = R.string.notification)
                        )
                    }
                }
            }


        }
    }
}

fun shareNotes(title: String, description: String, date: String, context: Context) {
    val content = """
     DailyNotes:
     --------------
  title   :   $title
     --------------
  Content :  $description
     
     --------------
   Date  $date        @hamoosoft
 """.trimIndent()
    val intent = Intent(Intent.ACTION_SEND)
    intent.apply {
        putExtra(Intent.EXTRA_SUBJECT,context.getString(R.string.app_name))
        putExtra(Intent.EXTRA_TEXT,content)


        type = "text/plan"

    }
    var chooser = Intent.createChooser(intent, context.getString(R.string.app_name))
    try {
        context.startActivity(chooser,null)
    }catch (e: ActivityNotFoundException){
        Toast.makeText(context,e.message, Toast.LENGTH_LONG).show()
    }
}