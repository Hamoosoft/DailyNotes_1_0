package com.soft.dailynotes.presentation.ui.notes.details

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.widget.DatePicker
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.soft.dailynotes.R
import java.util.Calendar

@Composable
fun ConfirmationMessage(@StringRes text: Int) {
    Text(
        text = stringResource(id = text),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                dimensionResource(id = R.dimen.large_padding)
            ),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesEntries(
    modifier: Modifier = Modifier,
    noteUiState: NotesUiState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    selectedColor: Color
) {
    Card(
        modifier = modifier.padding(horizontal = dimensionResource(id = R.dimen.large_padding)),
        elevation = dimensionResource(id = R.dimen.small_padding),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.medium_padding)),
        backgroundColor = selectedColor
    ) {
        Column {
            OutlinedTextField(
                value = noteUiState.title,
                onValueChange = { onTitleChange(it) },
                label = {
                    Text(
                        text = stringResource(id = R.string.title),
                        style = MaterialTheme.typography.labelMedium
                    )
                }, placeholder = {
                    Text(
                        text = stringResource(id = R.string.title),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary.copy(.5f)
                    )
                }, modifier = modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.large_padding))

            )
            OutlinedTextField(
                value = noteUiState.description,
                onValueChange = { onDescriptionChange(it) },
                label = {
                    Text(
                        text = stringResource(id = R.string.notes),
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.typeNote),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary.copy(.5f)
                    )
                },
                textStyle = TextStyle.Default.copy(
                    textAlign = TextAlign.Start,
                    textDecoration = TextDecoration.None
                ),
                modifier = modifier
                    .heightIn(min = dimensionResource(id = R.dimen.min_height))
                    .padding(horizontal = dimensionResource(id = R.dimen.large_padding))
                    .fillMaxWidth(),


                )
            IconButton(
                onClick = { /*TODO*/ }, modifier = modifier.align(alignment = Alignment.End)
            ) {
                Icon(
                    painterResource(id = R.drawable.baseline_image),
                    contentDescription = null
                )
            }

        }
    }


}

@Composable
fun ColorItem(modifier: Modifier = Modifier, color: Color, onClick: (Color) -> Unit) {
    Card(
        modifier = modifier
            .size(dimensionResource(id = R.dimen.color_card_size))
            .padding(
                vertical = dimensionResource(id = R.dimen.small_padding),
                horizontal = dimensionResource(
                    id = R.dimen.medium_padding
                )
            )
            .clickable { onClick(color) },
        backgroundColor = color, elevation = dimensionResource(id = R.dimen.medium_padding),
        content = {}, shape = MaterialTheme.shapes.extraLarge
    )

}

@Composable
fun ButtonNav(modifier: Modifier = Modifier, showNotification: () -> Unit) {
    BottomAppBar(
        cutoutShape = MaterialTheme.shapes.small.copy(
            CornerSize(50)
        ), backgroundColor = MaterialTheme.colorScheme.primary
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(end = dimensionResource(id = R.dimen.medium_padding))
        ) {
            BottomNavigationItem(selected = false, onClick = { /*TODO*/ }, icon = {
                Icon(imageVector = Icons.Filled.Share, contentDescription = null)
            }, unselectedContentColor = MaterialTheme.colorScheme.onPrimary)
            BottomNavigationItem(selected = false, onClick = { showNotification() }, icon = {
                Icon(imageVector = Icons.Filled.Notifications, contentDescription = null)
            }, unselectedContentColor = MaterialTheme.colorScheme.onPrimary)
            BottomNavigationItem(selected = false, onClick = { /*TODO*/ }, icon = {
                Icon(imageVector = Icons.Filled.Info, contentDescription = null)
            }, unselectedContentColor = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReminderDialog(onDismiss: () -> Unit, reminderData: (Long) -> Unit, context: Context) {
    var pickDateDialog by rememberSaveable { mutableStateOf(false) }
    var dateAndTime by rememberSaveable { mutableStateOf(0L) }
    if (pickDateDialog) {
        GetDateAndTime(
            context = context,
            onDismiss = { pickDateDialog = !pickDateDialog },
            onConfirm = {
                dateAndTime = it
                try {
                    reminderData(dateAndTime)
                    onDismiss()
                } catch (e: Exception) {
                    e.localizedMessage?.let { it1 -> Log.e("DatePicker Error", it1) }
                }

            })
    }
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Surface(
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.large_padding)),
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.large_padding))

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Text(
                    text = stringResource(R.string.title_reminder),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                )

                ListItem(
                    text = { Text(text = stringResource(R.string.schedule_5_seconds)) },
                    modifier = Modifier.clickable {
                        reminderData(5000L)
                        onDismiss()
                    }
                )
                ListItem(
                    text = { Text(text = stringResource(R.string.schedule_8_minutes)) },
                    modifier = Modifier.clickable {
                        reminderData(8 * 60 * 1000L)
                        onDismiss()
                    }
                )
                ListItem(
                    text = { Text(text = stringResource(R.string.schedule_1_day)) },
                    modifier = Modifier.clickable {
                        reminderData(24 * 60 * 60 * 1000L)
                        onDismiss()
                    }
                )
                ListItem(
                    text = { Text(text = stringResource(R.string.schedule_1_week)) },
                    modifier = Modifier.clickable {
                        reminderData(7 * 24 * 60 * 60 * 1000L)
                        onDismiss()
                    }
                )
                ListItem(
                    text = { Text(text = "choose time.. ") },
                    modifier = Modifier.clickable {
                        pickDateDialog = !pickDateDialog

                    }
                )


            }
        }
    }
}

@Composable
fun GetDateAndTime(
    modifier: Modifier = Modifier,
    context: Context,
    onDismiss: () -> Unit,
    onConfirm: (Long) -> Unit
) {
    var chosenYear by rememberSaveable { mutableStateOf(0) }
    var chosenMonth by rememberSaveable { mutableStateOf(0) }
    var chosenDay by rememberSaveable { mutableStateOf(0) }
    var chosenHour by rememberSaveable { mutableStateOf(0) }
    var chosenMin by rememberSaveable { mutableStateOf(0) }

    val userSelectedDateTime = Calendar.getInstance()

    userSelectedDateTime.set(chosenYear, chosenMonth, chosenDay, chosenHour, chosenMin)

    val todayDateTime = Calendar.getInstance()

    val delayInMillis =
        (userSelectedDateTime.timeInMillis) - (todayDateTime.timeInMillis)


    val datePickerDialog = datePickerDialog(context = context,
        dateChosen = { year, month, day ->
            chosenYear = year
            chosenMonth = month
            chosenDay = day
        })
    val timePickerDialog = timePickerDialog(context = context,
        timeChosen = { hour, minute ->
            chosenHour = hour
            chosenMin = minute
        })
    Dialog(onDismissRequest = { onDismiss() }) {

        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.large_padding))
                .heightIn(200.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
            ) {

                Text(
                    text = "${userSelectedDateTime.time}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp, color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.large_padding))
                )

                Spacer(modifier = modifier.padding(dimensionResource(id = R.dimen.large_padding)))
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
                        onClick = { datePickerDialog.show() },
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary),
                        modifier = modifier
                            .weight(1f)
                            .padding(end = dimensionResource(id = R.dimen.small_padding))
                    ) {
                        Text(
                            text = "Pick Date",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    TextButton(
                        onClick = { timePickerDialog.show() },
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary),
                        modifier = modifier
                            .weight(1f)
                            .padding(start = dimensionResource(id = R.dimen.small_padding))
                    ) {
                        Text(
                            text = "pick Time",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
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
                        onClick = {
                            onConfirm(delayInMillis)
                            onDismiss()
                        },
                        modifier = modifier
                            .padding(horizontal = dimensionResource(id = R.dimen.large_padding)),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary)
                    ) {
                        Text(
                            text = "ok",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary,


                            )

                    }
                    TextButton(
                        onClick = { onDismiss() },
                        modifier = modifier
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
}


@Composable
fun datePickerDialog(
    context: Context,
    dateChosen: (Int, Int, Int) -> Unit
): DatePickerDialog {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.get(Calendar.DAY_OF_MONTH)
    val mDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, mYears: Int, mMonth: Int, mDayOfMonth: Int ->
            dateChosen(mYears, mMonth, mDayOfMonth)
        }, year, month, day
    )
    return mDatePickerDialog
}

@Composable
fun timePickerDialog(
    context: Context,
    timeChosen: (Int, Int) -> Unit
): TimePickerDialog {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay: Int, minuteOfHour: Int ->
            timeChosen(hourOfDay, minuteOfHour)
        }, hour, minute, false
    )
    return timePickerDialog
}