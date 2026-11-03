/**
 * Veritm Project (C) 2026
 * Licensed under GPL-3.0 | See git history for contributors
 */

package com.veritm.music.ui.theme

import android.graphics.Bitmap
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import com.materialkolor.score.Score

val DefaultThemeColor = VeritmBlue

private val VeritmShapes =
    Shapes(
        extraSmall = RoundedCornerShape(10.dp),
        small = RoundedCornerShape(14.dp),
        medium = RoundedCornerShape(20.dp),
        large = RoundedCornerShape(28.dp),
        extraLarge = RoundedCornerShape(36.dp),
    )

@Composable
fun VeritmTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    pureBlack: Boolean = false,
    themeColor: Color = DefaultThemeColor,
    dynamicTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme =
        remember(darkTheme, pureBlack, themeColor, dynamicTheme) {
            val base = if (darkTheme) VeritmDarkColorScheme else VeritmLightColorScheme
            val accent = if (themeColor == DefaultThemeColor) base else base.withAccent(themeColor, darkTheme)
            val styled = if (dynamicTheme) accent.withDynamicGlassTint(themeColor, darkTheme) else accent
            if (darkTheme && pureBlack) styled.pureBlack(true) else styled
        }

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = VeritmShapes,
        content = content,
    )
}

fun Bitmap.extractThemeColor(): Color =
    Color(
        Palette.from(this)
            .maximumColorCount(8)
            .generate()
            .rankedColors(1, DefaultThemeColor.toArgb())
            .first(),
    )

internal fun Palette.rankedColors(
    desiredColorCount: Int,
    fallbackColor: Int,
): List<Int> = Score.score(
    swatches.associate { it.rgb to it.population },
    desiredColorCount,
    fallbackColor,
    true,
)

fun ColorScheme.pureBlack(apply: Boolean) =
    if (apply) copy(surface = Color.Black, background = Color.Black) else this

val ColorSaver =
    object : Saver<Color, Int> {
        override fun restore(value: Int): Color = Color(value)
        override fun SaverScope.save(value: Color): Int = value.toArgb()
    }
