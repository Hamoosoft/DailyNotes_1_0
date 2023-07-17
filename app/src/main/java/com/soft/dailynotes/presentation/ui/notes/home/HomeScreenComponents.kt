package com.soft.dailynotes.presentation.ui.notes.home

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.soft.dailynotes.R
import com.soft.dailynotes.data.repositoryImp.notesorder.NoteOrderType
import com.soft.dailynotes.data.repositoryImp.notesorder.NotesOrderBy
import com.soft.dailynotes.domain.models.Notes
import com.soft.dailynotes.presentation.ui.theme.DailyNotesTheme
import com.soft.dailynotes.presentation.ui.utils.NavigationItem
import com.soft.dailynotes.presentation.ui.utils.Utils
import shareNotes
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    text: String,
    onSearchChanged: (String) -> Unit,
    isGridLayoutChanged: () -> Unit,
    isGridLayout: Boolean

) {
    OutlinedTextField(
        value = text,
        colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = MaterialTheme.colorScheme.primaryContainer),
        onValueChange = { onSearchChanged(it) },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        shape = RoundedCornerShape(45),
        placeholder = { Text(text = "search by title...", color = Color.LightGray) },
        textStyle = MaterialTheme.typography.titleMedium,
        modifier = modifier
            .fillMaxWidth()

            .padding(
                dimensionResource(id = R.dimen.medium_padding)
            ),
        trailingIcon = {
            IconButton(onClick = isGridLayoutChanged) {
                Icon(
                    painterResource(id = if (isGridLayout) R.drawable.baseline_view_list else R.drawable.baseline_grid_view),
                    contentDescription = null
                )
            }
        },
        leadingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            }
        }
    )
}

@Composable
fun OrderByItems(
    modifier: Modifier = Modifier,
    onOrderByClick: (NotesOrderBy) -> Unit,
    onOrderTypeClick: (NoteOrderType) -> Unit,
    homeNotesUiState: HomeNotesUiState,

    ) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RadioButton(
                selected = homeNotesUiState.notesOrderBy == NotesOrderBy.Color,
                onClick = { onOrderByClick(NotesOrderBy.Color) })
            Text(
                text = stringResource(id = R.string.color),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            RadioButton(
                selected = homeNotesUiState.notesOrderBy == NotesOrderBy.Title,
                onClick = { onOrderByClick(NotesOrderBy.Title) })
            Text(
                text = stringResource(id = R.string.title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            RadioButton(
                selected = homeNotesUiState.notesOrderBy == NotesOrderBy.Date,
                onClick = { onOrderByClick(NotesOrderBy.Date) })
            Text(
                text = stringResource(id = R.string.date),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )


        }
        Divider()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RadioButton(
                selected = homeNotesUiState.notesOrderType == NoteOrderType.Ascending,
                onClick = { onOrderTypeClick(NoteOrderType.Ascending) })
            Text(
                text = stringResource(id = R.string.ascending),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            RadioButton(
                selected = homeNotesUiState.notesOrderType == NoteOrderType.Descending,
                onClick = { onOrderTypeClick(NoteOrderType.Descending) })
            Text(
                text = stringResource(id = R.string.descending),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}



@Composable
fun currentRoute(navController: NavHostController): String? {
    val route by navController.currentBackStackEntryAsState()
    return route?.destination?.route
}

@Composable
fun BottonNavigation(modifier: Modifier = Modifier, navController: NavHostController) {
    val currentRoute = currentRoute(navController = navController)
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary
    ) {
        NavigationItem.navigationItems.forEach {
            BottomNavigationItem(
                selected = currentRoute == it.title,
                onClick = { navController.navigate(it.title) },
                icon = {
                    Icon(
                        painterResource(id = it.imgRes),
                        contentDescription = it.title
                    )
                },
                label = {
                    Text(
                        text = it.title, style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = modifier.padding(
                            vertical = dimensionResource(
                                id = R.dimen.medium_padding
                            )
                        )
                    )
                },
                alwaysShowLabel = false,
                modifier = modifier.padding(
                    end = dimensionResource(
                        id = R.dimen.large_padding
                    )
                ),
                selectedContentColor = MaterialTheme.colorScheme.inversePrimary,
                unselectedContentColor = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesItem(
    modifier: Modifier = Modifier,
    notes: Notes,
    homeNoteViewModel: HomeViewModel,
    applyNotificationDialog: (String) -> Unit,
    onLongClick: () -> Unit,
    onClick: (Int) -> Unit,
    context: Context,
    onArchiveIconClick: (Notes) -> Unit
) {
    Card(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.small_padding))
            .heightIn(
                min = dimensionResource(
                    id = R.dimen.extra_min_height
                )
            )
            .fillMaxWidth()
            .combinedClickable(onLongClick = { onLongClick() }, onClick = { onClick(notes.noteId) })
            .clip(
                RoundedCornerShape(dimensionResource(id = R.dimen.medium_padding))
            ),
        elevation = dimensionResource(id = R.dimen.small_padding),
        backgroundColor = if (homeNoteViewModel.darkMode()) Utils.colorsDark[notes.color] else Utils.colors[notes.color]
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
            Spacer(modifier = modifier.padding(dimensionResource(id = R.dimen.small_padding)))
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
            Divider()
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ArchiveIcon(onArchiveIconClick = { onArchiveIconClick(notes) })
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
                if (notes.schedule.isNotBlank()) {
                    IconButton(
                        onClick = { applyNotificationDialog(notes.title) }
                    ) {
                        Icon(
                            painterResource(id = R.drawable.baseline_notifications_active),
                            contentDescription = null
                        )
                    }
                } else {
                    IconButton(
                        onClick = { applyNotificationDialog(notes.title) }
                    ) {
                        Icon(
                            painterResource(id = R.drawable.baseline_notifications_none),
                            contentDescription = null
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun ArchiveIcon(onArchiveIconClick: () -> Unit) {
    IconButton(
        onClick = { onArchiveIconClick() }
    ) {
        Icon(
            painterResource(id = R.drawable.baseline_archive),
            contentDescription = null
        )
    }
}


@Composable
fun HomeNotesTopAppBar(
    modifier: Modifier = Modifier,
    isNavigateBack: Boolean = false,
    isHomeNotEmpty: Boolean = false,
    title: String,
    onSignOut: () -> Unit = {},
    animatedVisibilityStatus: () -> Unit = {},
    navigateUp: () -> Unit = {}
) {
    TopAppBar(navigationIcon = {
        if (isNavigateBack) {
            IconButton(onClick = {
                navigateUp()
            }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        } else {
            IconButton(onClick = {
                onSignOut()
            }) {
                Icon(imageVector = Icons.Default.ExitToApp, contentDescription = null)
            }

        }

    }, actions = {
        if (!isNavigateBack && isHomeNotEmpty) {
            IconButton(onClick = {
                animatedVisibilityStatus()
            }) {
                Icon(painterResource(id = R.drawable.baseline_sort), contentDescription = null)
            }
        }

    },
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        backgroundColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    )
}


fun getDate(time: Date): String {
    val cal = SimpleDateFormat("dd - MM - yyyy hh:mm", Locale.GERMANY)
    return cal.format(time)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NotePreview() {
    DailyNotesTheme {

    }
}

@Composable
fun DeleteNoteDialog(
    modifier: Modifier = Modifier,
    note: Notes,
    onConfirmClick: () -> Unit,
    onArchiveClick: () -> Unit,
    onDismissClick: () -> Unit
) {

    Dialog(onDismissRequest = { onDismissClick() }) {
        Card(
            modifier = modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            elevation = dimensionResource(id = R.dimen.card_elevation)
        ) {
            Column(
                modifier = modifier.background(MaterialTheme.colorScheme.primary),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    text = stringResource(id = R.string.deleteNoteDialogTitle),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Divider(modifier = modifier.padding(dimensionResource(id = R.dimen.medium_padding)))
                Text(
                    text = stringResource(id = R.string.deleteNoteDialogMessage, note.title),
                    style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary, modifier = modifier.padding(
                        horizontal = dimensionResource(
                            id = R.dimen.large_padding
                        )
                    )
                )
                Spacer(modifier = modifier.padding(dimensionResource(id = R.dimen.medium_padding)))
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = dimensionResource(
                                id = R.dimen.large_padding
                            )
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    TextButton(
                        onClick = { onConfirmClick() },
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary),
                        modifier = modifier
                            .weight(1f)
                            .padding(end = dimensionResource(id = R.dimen.small_padding))
                    ) {
                        Text(
                            text = stringResource(id = R.string.delete),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    TextButton(
                        onClick = { onArchiveClick() },
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary),
                        modifier = modifier
                            .weight(1f)
                            .padding(start = dimensionResource(id = R.dimen.small_padding))
                    ) {
                        Text(
                            text = stringResource(id = R.string.archive),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                Spacer(modifier = modifier.padding(dimensionResource(id = R.dimen.medium_padding)))
                TextButton(
                    onClick = { onDismissClick() },
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(id = R.dimen.large_padding)),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary)
                ) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary,


                        )

                }
            }
        }
    }
}