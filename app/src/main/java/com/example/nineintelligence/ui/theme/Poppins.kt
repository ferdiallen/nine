package com.example.nineintelligence.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.nineintelligence.R

object Poppins {
    val fonts = FontFamily(
        listOf(
            Font(
                R.font.poppins_regular, weight = FontWeight.Normal
            ),
            Font(
                R.font.poppins_bold, weight = FontWeight.Bold
            ),
            Font(
                R.font.poppins_bolditalic, weight = FontWeight.Bold, style = FontStyle.Italic
            ),
            Font(
                R.font.poppins_light, weight = FontWeight.Light
            ),
            Font(
                R.font.poppins_lightitalic, weight = FontWeight.Light, style = FontStyle.Italic
            ),
            Font(
                R.font.poppins_extralight, weight = FontWeight.ExtraLight
            ),
            Font(
                R.font.poppins_extralightitalic,
                weight = FontWeight.ExtraLight,
                style = FontStyle.Italic
            ),
            Font(
                R.font.poppins_semibold,
                weight = FontWeight.SemiBold
            ),
            Font(
                R.font.poppins_semibolditalic,
                weight = FontWeight.SemiBold,
                style = FontStyle.Italic
            ),
        )
    )
}