package com.rashoooodi.gemnotecloud.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PaletteStyle
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val SeedPrimary = Color(0xFF65558F)
private val ExpressiveLightColorScheme by lazy {
    ColorScheme.fromSeed(
        seedColor = SeedPrimary,
        paletteStyle = PaletteStyle.Expressive
    )
}

private val ExpressiveDarkColorScheme by lazy {
    ColorScheme.fromSeed(
        seedColor = SeedPrimary,
        paletteStyle = PaletteStyle.Expressive,
        darkTheme = true
    )
}

@Composable
fun GemNoteTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor -> {
            val context = LocalContext.current
            if (useDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        useDarkTheme -> ExpressiveDarkColorScheme
        else -> ExpressiveLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}
