package ru.agapov.musiciansdiary.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryPurple,
    onPrimary = OnBackground,
    background = Background,
    onBackground = OnBackground,
    surface = SurfaceDark,
    onSurface = OnBackground,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariant,
    outline = OutlineDark,
    error = DeleteRed
)

@Composable
fun MusiciansDiaryTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}