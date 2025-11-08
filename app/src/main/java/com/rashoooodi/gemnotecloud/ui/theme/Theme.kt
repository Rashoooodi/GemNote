package com.rashoooodi.gemnotecloud.ui.theme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
private val DarkColors = darkColorScheme()
@Composable fun GemNoteTheme(content: @Composable () -> Unit) {
  MaterialTheme(colorScheme = DarkColors, typography = MaterialTheme.typography, content = content)
}
