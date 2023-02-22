package com.example.nineintelligence.util

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import com.example.nineintelligence.ui.theme.Poppins

@Composable
fun CustomText(
    text: String,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize: TextUnit = TextUnit.Unspecified,
    color: Color = Color.Unspecified
) {
    Text(
        text = text,
        modifier,
        fontWeight = fontWeight,
        fontSize = fontSize,
        fontFamily = Poppins.fonts
    )
}