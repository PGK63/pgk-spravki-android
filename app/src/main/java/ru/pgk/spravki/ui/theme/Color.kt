package ru.pgk.spravki.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFF2B59BB)
val Purple500 = Color(0xFF2B59BB)
val Purple700 = Color(0xFF2B59BB)
val Teal200 = Color(0xFF2B59BB)

val tintColor = Color(0xFF2B59BB)

@Composable
fun primaryTextColor(): Color {
    return if(isSystemInDarkTheme()){
        Color(0xFFFFFFFF)
    }else {
        Color(0xFF000000)
    }
}

@Composable
fun primaryBackgroundColor(): Color {
    return if(isSystemInDarkTheme()){
        Color(0xFF2C2C2C)
    }else {
        Color(0xFFFFFFFF)
    }
}

@Composable
fun secondaryBackgroundColor(): Color {
    return if(isSystemInDarkTheme()){
        Color(0xFF444141)
    }else {
        Color(0xFFE4E2E2)
    }
}