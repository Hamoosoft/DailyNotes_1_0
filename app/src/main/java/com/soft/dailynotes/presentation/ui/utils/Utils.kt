package com.soft.dailynotes.presentation.ui.utils

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.soft.dailynotes.R

object Utils {

    val colors = listOf(
        Color(0xFFFFDAD6),
        Color(0xFF9871F1),
        Color(0xFF6750A4),
        Color(0xFF8AC4F1),
        Color(0xFF996921),
        Color(0xFF7E5260),
        Color(0xFFAA9F8F),
        Color(0xFFC7E0AB),
        Color(0xFFC3FBFF),
        Color(0xFFCAC4CF),


        )
    val colorsDark = listOf(
        Color(0xFF444444),
        Color(0xFF50343D),
        Color(0xFF473837),
        Color(0xFF533D1C),
        Color(0xFF3A504E),
        Color(0xFF284147),
        Color(0xFF343838),
        Color(0xFF3B4683),
        Color(0xFF867A0C),
        Color(0xFF4F20A2),


        )
}

data class NavigationItem(val title: String, @DrawableRes val imgRes: Int) {

    companion object {
        val navigationItems = listOf(
            NavigationItem("Home", R.drawable.baseline_home),
            NavigationItem("Archive", R.drawable.baseline_archive),
            NavigationItem("Settings", R.drawable.baseline_settings)
        )

    }
}

object Language {
    val languages = mapOf<Boolean, Int>(true to R.string.english, false to R.string.deutsch)

}