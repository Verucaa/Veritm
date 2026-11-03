/**
 * VERITM Project (C) 2026
 * Licensed under GPL-3.0 | See git history for contributors
 */

package com.veritm.music.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.luminance

// Core identity palette.
val VeritmBlue = Color(0xFF9AF3D5)
val VeritmCyan = Color(0xFF49D6B2)
val VeritmBackground = Color(0xFF030D0B)
val VeritmSurface = Color(0xFF071713)
val VeritmElevated = Color(0xFF102820)
val VeritmTextPrimary = Color(0xFFF4FFF9)
val VeritmTextSecondary = Color(0xFFA1B9B0)
val VeritmDivider = Color(0xFF31574D)

val VeritmDarkColorScheme =
    darkColorScheme(
        primary = VeritmBlue,
        onPrimary = Color(0xFF04221A),
        primaryContainer = Color(0xFF154B3B),
        onPrimaryContainer = Color(0xFFCCF8E7),
        secondary = VeritmCyan,
        onSecondary = Color(0xFF03251D),
        secondaryContainer = Color(0xFF0C3A2D),
        onSecondaryContainer = Color(0xFFB5F4DD),
        tertiary = Color(0xFFA9E88E),
        onTertiary = Color(0xFF15230B),
        tertiaryContainer = Color(0xFF29411D),
        onTertiaryContainer = Color(0xFFD3F7BF),
        background = VeritmBackground,
        onBackground = VeritmTextPrimary,
        surface = VeritmSurface,
        onSurface = VeritmTextPrimary,
        surfaceVariant = Color(0xFF12251F),
        onSurfaceVariant = VeritmTextSecondary,
        surfaceContainerLowest = Color(0xFF020A08),
        surfaceContainerLow = Color(0xCC0A1B16),
        surfaceContainer = Color(0xD90D211B),
        surfaceContainerHigh = Color(0xE6132D25),
        surfaceContainerHighest = Color(0xF01B392F),
        surfaceTint = VeritmBlue,
        inverseSurface = Color(0xFFDCEBE4),
        inverseOnSurface = VeritmSurface,
        inversePrimary = Color(0xFF17634C),
        outline = VeritmDivider,
        outlineVariant = Color(0xFF203D34),
        error = Color(0xFFFF8A83),
        onError = Color(0xFF370B08),
        errorContainer = Color(0xFF641C17),
        onErrorContainer = Color(0xFFFFDAD5),
        scrim = Color.Black,
    )

val VeritmLightColorScheme =
    lightColorScheme(
        primary = Color(0xFF126A50),
        onPrimary = Color.White,
        primaryContainer = Color(0xFFB8F1D9),
        onPrimaryContainer = Color(0xFF05271C),
        secondary = Color(0xFF147F68),
        onSecondary = Color.White,
        secondaryContainer = Color(0xFFC9F4E8),
        onSecondaryContainer = Color(0xFF05271C),
        tertiary = Color(0xFF497832),
        onTertiary = Color.White,
        tertiaryContainer = Color(0xFFD5F1C4),
        onTertiaryContainer = Color(0xFF18260E),
        background = Color(0xFFF4FBF7),
        onBackground = Color(0xFF10221B),
        surface = Color(0xFFF8FFFB),
        onSurface = Color(0xFF10221B),
        surfaceVariant = Color(0xFFE0EEE7),
        onSurfaceVariant = Color(0xFF465B52),
        surfaceContainerLowest = Color.White,
        surfaceContainerLow = Color(0xFFF2FAF5),
        surfaceContainer = Color(0xFFE8F4ED),
        surfaceContainerHigh = Color(0xFFDDECE4),
        surfaceContainerHighest = Color(0xFFCFDFD6),
        surfaceTint = Color(0xFF126A50),
        inverseSurface = VeritmSurface,
        inverseOnSurface = VeritmTextPrimary,
        inversePrimary = VeritmBlue,
        outline = Color(0xFF81988D),
        outlineVariant = Color(0xFFBACBC1),
        error = Color(0xFFBA1A1A),
        onError = Color.White,
        errorContainer = Color(0xFFFFDAD6),
        onErrorContainer = Color(0xFF410002),
        scrim = Color.Black,
    )

// Override the primary accent family while keeping the VERITM surfaces intact.
internal fun ColorScheme.withAccent(
    accent: Color,
    dark: Boolean,
): ColorScheme {
    val onAccent = if (accent.luminance() > 0.5f) Color(0xFF05201A) else Color.White
    val containerMix = if (dark) 0.35f else 0.18f
    val container = lerp(surface, accent, containerMix)
    val onContainer = if (container.luminance() > 0.5f) Color(0xFF05201A) else Color(0xFFB8F1D9)
    return copy(
        primary = accent,
        onPrimary = onAccent,
        primaryContainer = container,
        onPrimaryContainer = onContainer,
        surfaceTint = accent,
        inversePrimary = accent,
    )
}

internal fun ColorScheme.withDynamicGlassTint(accent: Color, dark: Boolean): ColorScheme =
    if (dark) {
        copy(
            background = lerp(VeritmBackground, accent, 0.12f),
            surface = lerp(VeritmSurface, accent, 0.10f),
            surfaceVariant = lerp(Color(0xFF12251F), accent, 0.15f),
            surfaceContainerLowest = lerp(Color(0xFF020A08), accent, 0.08f),
            surfaceContainerLow = lerp(Color(0xFF0A1B16), accent, 0.12f).copy(alpha = 0.88f),
            surfaceContainer = lerp(Color(0xFF0D211B), accent, 0.16f).copy(alpha = 0.90f),
            surfaceContainerHigh = lerp(Color(0xFF132D25), accent, 0.20f).copy(alpha = 0.94f),
            surfaceContainerHighest = lerp(Color(0xFF1B392F), accent, 0.24f).copy(alpha = 0.97f),
            outline = lerp(VeritmDivider, accent, 0.25f),
            outlineVariant = lerp(Color(0xFF203D34), accent, 0.18f),
        )
    } else {
        copy(
            background = lerp(Color(0xFFF4FBF7), accent, 0.045f),
            surface = lerp(Color(0xFFF8FFFB), accent, 0.06f),
            surfaceVariant = lerp(Color(0xFFE0EEE7), accent, 0.08f),
            surfaceContainerLowest = lerp(Color.White, accent, 0.025f),
            surfaceContainerLow = lerp(Color(0xFFF2FAF5), accent, 0.04f),
            surfaceContainer = lerp(Color(0xFFE8F4ED), accent, 0.06f),
            surfaceContainerHigh = lerp(Color(0xFFDDECE4), accent, 0.08f),
            surfaceContainerHighest = lerp(Color(0xFFCFDFD6), accent, 0.10f),
            outline = lerp(Color(0xFF81988D), accent, 0.16f),
            outlineVariant = lerp(Color(0xFFBACBC1), accent, 0.12f),
        )
    }
