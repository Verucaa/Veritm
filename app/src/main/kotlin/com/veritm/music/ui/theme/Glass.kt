/**
 * Metrolist Project (C) 2026
 * Licensed under GPL-3.0 | See git history for contributors
 */

package com.veritm.music.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/** Translucent surface colors for the frosted-glass look. */
@Immutable
data class GlassTokens(
    val enabled: Boolean,
    val fill: Color,
    val fillStrong: Color,
    val border: Color,
    val sheen: Color,
) {
    companion object {
        val Disabled =
            GlassTokens(
                enabled = false,
                fill = Color.Transparent,
                fillStrong = Color.Transparent,
                border = Color.Transparent,
                sheen = Color.Transparent,
            )
    }
}

val LocalGlassTokens = staticCompositionLocalOf { GlassTokens.Disabled }

/** Pure: maps the dark/light flag to the glass palette. */
fun glassTokens(dark: Boolean, accent: Color): GlassTokens =
    if (dark) {
        GlassTokens(
            enabled = true,
            fill = Color.White.copy(alpha = 0.08f),
            fillStrong = Color.White.copy(alpha = 0.12f),
            border = Color.White.copy(alpha = 0.16f),
            sheen = Color.White.copy(alpha = 0.22f),
        )
    } else {
        GlassTokens(
            enabled = true,
            fill = Color.White.copy(alpha = 0.45f),
            fillStrong = Color.White.copy(alpha = 0.60f),
            border = Color.Black.copy(alpha = 0.08f),
            sheen = Color.White.copy(alpha = 0.60f),
        )
    }

/** No-op when glass is disabled, so surfaces keep their previous look. */
@Composable
fun Modifier.glassSurface(
    shape: Shape,
    strong: Boolean = false,
): Modifier {
    val tokens = LocalGlassTokens.current
    if (!tokens.enabled) return this
    val fill = if (strong) tokens.fillStrong else tokens.fill
    return this
        .background(fill, shape)
        .background(
            Brush.verticalGradient(listOf(tokens.sheen, Color.Transparent)),
            shape,
        )
        .border(1.dp, tokens.border, shape)
}

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.large,
    strong: Boolean = false,
    content: @Composable () -> Unit,
) {
    val tokens = LocalGlassTokens.current
    Surface(
        modifier = if (tokens.enabled) modifier.glassSurface(shape, strong) else modifier,
        shape = shape,
        color = if (tokens.enabled) Color.Transparent else MaterialTheme.colorScheme.surfaceContainer,
        content = content,
    )
}
