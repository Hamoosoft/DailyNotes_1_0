package com.soft.dailynotes.presentation.ui.notes.settings

import android.Manifest.permission.CALL_PHONE
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomAppBar
import androidx.compose.material.ContentAlpha
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.soft.dailynotes.R
import com.soft.dailynotes.presentation.ui.notes.home.BottonNavigation
import com.soft.dailynotes.presentation.ui.notes.home.HomeNotesTopAppBar
import com.soft.dailynotes.presentation.ui.utils.Constants.CONTACT_NUMBER
import com.soft.dailynotes.presentation.ui.utils.Constants.EMAIL_Address
import com.soft.dailynotes.presentation.ui.utils.Constants.SMS_NUMBER
import com.soft.dailynotes.presentation.ui.utils.Constants.WHATSAPP_NUMBER
import com.soft.dailynotes.presentation.ui.utils.Language


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onNavigateUp: () -> Unit = {},
    onAboutUsClick: () -> Unit
) {
    var viewModel: SettingsViewModel = hiltViewModel()
    val uiState = viewModel._settingsUiState.collectAsState().value
    val langState = viewModel.uiState.collectAsState().value
    val context = LocalContext.current as Activity
    var contactUsDialogState by rememberSaveable { mutableStateOf(false) }
    Scaffold(topBar = {
        HomeNotesTopAppBar(
            title = stringResource(id = R.string.settings),
            isNavigateBack = true,
            navigateUp = { onNavigateUp() })
    }, bottomBar = {
        BottomAppBar(
            backgroundColor = MaterialTheme.colorScheme.primary
        ) {
            BottonNavigation(navController = navController)
        }
    }) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LanguageSettings(
                isEnglish = langState.isEnglish,
                onLanguageOptionChanged = { viewModel.addLanguageSetting(it) })
            DarkMode(darkTheme = {
                viewModel.addSetting(it)
            }, modeValue = uiState.darkMode)
            Info(
                onContactUsIconClick = { contactUsDialogState = !contactUsDialogState },
                onAboutUsClick = { onAboutUsClick() })
        }

    }
    if (contactUsDialogState) {
        ContactUs(
            callUsLink = { callUsAction(context = context) },
            sentUsEmailLink = { setUsAnEmail(context = context) },
            sentUsSMSLink = { sentSmsAction(context = context) },
            sentUsWhatsappLink = { sentWhatsappAction(context = context) }) {
            contactUsDialogState = !contactUsDialogState
        }
    }
}

@Composable
fun LanguageSettings(
    modifier: Modifier = Modifier,
    isEnglish: Boolean,
    onLanguageOptionChanged: (Boolean) -> Unit
) {
    Card(
        modifier = modifier.padding(dimensionResource(id = R.dimen.large_padding)),
        border = BorderStroke(width = 0.5.dp, color = MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.small_padding)),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.language),
                style = MaterialTheme.typography.titleMedium
            )
            CompositionLocalProvider(values = arrayOf(LocalContentAlpha provides ContentAlpha.disabled)) {
                Text(
                    text = stringResource(id = R.string.chooseLanguage),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Row() {
                Language.languages.forEach {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = it.key == isEnglish,
                            onClick = {
                                onLanguageOptionChanged(!isEnglish)
                            })
                        Text(
                            text = stringResource(id = it.value),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

        }
    }

}

@Composable
fun DarkMode(modifier: Modifier = Modifier, darkTheme: (Boolean) -> Unit, modeValue: Boolean) {

    Card(
        modifier = modifier.padding(dimensionResource(id = R.dimen.large_padding)),
        border = BorderStroke(width = 0.5.dp, color = MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.small_padding)),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.dark_mode),
                style = MaterialTheme.typography.titleMedium
            )
            CompositionLocalProvider(values = arrayOf(LocalContentAlpha provides ContentAlpha.disabled)) {
                Text(
                    text = stringResource(id = R.string.chooseMode),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Switch(
                checked = modeValue,
                onCheckedChange = {

                    darkTheme(it)
                },
                thumbContent = {
                    Icon(
                        painterResource(R.drawable.baseline_dark_mode),
                        contentDescription = null, modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.large_padding))
                            .size(dimensionResource(id = R.dimen.color_card_size))
                    )
                },

                )

        }
    }
}

@Composable
fun Info(
    modifier: Modifier = Modifier,
    onContactUsIconClick: () -> Unit,
    onAboutUsClick: () -> Unit
) {

    Card(
        modifier = modifier.padding(dimensionResource(id = R.dimen.large_padding)),
        border = BorderStroke(width = 0.5.dp, color = MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.small_padding)),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.clickable { onContactUsIconClick() }) {
                CompositionLocalProvider(values = arrayOf(LocalContentAlpha provides ContentAlpha.disabled)) {
                    IconButton(onClick = {
                        onContactUsIconClick()

                    }) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = stringResource(id = R.string.contact)
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.contact),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.clickable { onAboutUsClick() }) {
                CompositionLocalProvider(values = arrayOf(LocalContentAlpha provides ContentAlpha.disabled)) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = stringResource(id = R.string.about)
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.about),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                CompositionLocalProvider(values = arrayOf(LocalContentAlpha provides ContentAlpha.disabled)) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = stringResource(id = R.string.share_app)
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.share_app),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }


        }
    }
}

@Composable
fun ContactUs(
    modifier: Modifier = Modifier,
    callUsLink: () -> Unit,
    sentUsEmailLink: () -> Unit,
    sentUsSMSLink: () -> Unit,
    sentUsWhatsappLink: () -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismiss() }, properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        Card(
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
        ) {
            IconButton(
                onClick = { onDismiss() }, modifier = modifier
                    .align(alignment = Alignment.Start)
                    .padding(
                        dimensionResource(id = R.dimen.small_padding)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.cancel)
                )
            }
            Divider()
            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ContactForm(
                        modifier = modifier
                            .padding(dimensionResource(id = R.dimen.medium_padding)),
                        drawable = R.drawable.baseline_call,
                        title = stringResource(id = R.string.call_us),
                        onClick = {
                            callUsLink()
                            onDismiss()
                        }
                    )
                    ContactForm(
                        modifier = modifier
                            .padding(dimensionResource(id = R.dimen.medium_padding)),
                        drawable = R.drawable.baseline_whatsapp,
                        title = stringResource(id = R.string.send_whatsapp),
                        onClick = {
                            sentUsWhatsappLink()
                            onDismiss()
                        }
                    )
                }
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ContactForm(
                        modifier = modifier
                            .padding(dimensionResource(id = R.dimen.medium_padding)),
                        drawable = R.drawable.baseline_email,
                        title = stringResource(id = R.string.send_email),
                        onClick = {
                            sentUsEmailLink()
                            onDismiss()
                        }
                    )
                    ContactForm(
                        modifier = modifier
                            .padding(dimensionResource(id = R.dimen.medium_padding)),
                        drawable = R.drawable.baseline_message,
                        title = stringResource(id = R.string.send_message),
                        onClick = {
                            sentUsSMSLink()
                            onDismiss()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ContactForm(
    modifier: Modifier = Modifier,
    @DrawableRes drawable: Int,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(.5f)),
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.small_padding))
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.clickable { onClick() }) {
            Image(
                painterResource(id = drawable),
                contentDescription = title,
                modifier = modifier
                    .size(
                        dimensionResource(id = R.dimen.extra_min_height)
                    )
                    .padding(dimensionResource(id = R.dimen.medium_padding))
            )
            Text(text = title, style = MaterialTheme.typography.titleMedium)
        }
    }
}


fun callUsAction(context: Context) {
    val uri = Uri.parse(CONTACT_NUMBER)
    try {
        if (ContextCompat.checkSelfPermission(
                context,
                CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity, arrayOf(CALL_PHONE),
                1
            )

            ActivityCompat.requestPermissions(context, arrayOf(CALL_PHONE), 1)
        }
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = uri
        context.startActivity(intent)
    } catch (e: Exception) {
        Log.e(context.getString(R.string.call_usError), e.localizedMessage ?: "")
    }
}

fun sentSmsAction(context: Context) {
    val uri = Uri.fromParts("sms", SMS_NUMBER, null)
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = uri
        context.startActivity(intent)
    } catch (e: Exception) {
        Log.e(context.getString(R.string.call_usError), e.localizedMessage ?: "")
    }
}

fun sentWhatsappAction(context: Context) {
    val uri = Uri.parse("https://api.whatsapp.com/send?phone=$WHATSAPP_NUMBER")
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = uri
        context.startActivity(intent)
    } catch (e: Exception) {
        Log.e(context.getString(R.string.call_usError), e.localizedMessage ?: "")
    }
}


fun setUsAnEmail(context: Context) {
    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse(EMAIL_Address)
    }
    try {
        val shareIntent = Intent.createChooser(emailIntent, context.getString(R.string.sentTo))
        startActivity(context as Activity, shareIntent, null)
    } catch (e: Exception) {
        Log.e(context.getString(R.string.sent_email_error), e.localizedMessage ?: "")
    }
}


