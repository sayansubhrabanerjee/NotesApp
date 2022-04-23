package sayan.notesapp.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val ActiveGreen = Color(0x9932CD32)
val PureWhite = Color(0xFFFFFFFF)
val PureBlack = Color(0xFF000000)

val Colors.Green: Color
    @Composable
    get() = ActiveGreen

val Colors.Black: Color
    @Composable
    get() = PureBlack

val Colors.White: Color
    @Composable
    get() = PureWhite