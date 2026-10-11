package com.vmusic.music.ui.theme

import androidx.compose.ui.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class GlassTokensTest {
    @Test
    fun darkTokensAreTranslucentWhites() {
        val t = glassTokens(dark = true, accent = Color(0xFF1DB954))
        assertTrue(t.enabled)
        assertTrue(t.fill.alpha in 0.02f..0.2f)
        assertTrue(t.border.alpha in 0.05f..0.4f)
    }

    @Test
    fun lightTokensAreLegible() {
        val t = glassTokens(dark = false, accent = Color(0xFF1DB954))
        assertTrue(t.enabled)
        assertTrue(t.fill.alpha > 0.2f)
        assertTrue(t.border.alpha in 0.02f..0.3f)
    }

    @Test
    fun disabledIsFullyTransparent() {
        assertFalse(GlassTokens.Disabled.enabled)
        assertEquals(0f, GlassTokens.Disabled.fill.alpha, 0f)
        assertEquals(0f, GlassTokens.Disabled.border.alpha, 0f)
    }
}
