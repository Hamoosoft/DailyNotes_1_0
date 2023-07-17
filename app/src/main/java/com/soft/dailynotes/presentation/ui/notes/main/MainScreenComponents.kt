package com.soft.dailynotes.presentation.ui.notes.main

import android.app.Activity
import android.content.res.Configuration
import java.util.Locale

fun localeLange(activity: Activity, languageCode: String?) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val resources = activity.resources
    val config: Configuration = resources.configuration
    config.setLocale(locale)
    resources.updateConfiguration(config, resources.displayMetrics)
}