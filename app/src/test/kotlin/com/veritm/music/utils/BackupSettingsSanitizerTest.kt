package com.veritm.music.utils

import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.toMutablePreferences
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class BackupSettingsSanitizerTest {
    @Test
    fun removesSensitiveValuesAndKeepsOrdinarySettings() {
        val cookieKey = stringPreferencesKey("innerTubeCookie")
        val apiKey = stringPreferencesKey("openRouterApiKey")
        val themeKey = stringPreferencesKey("darkMode")
        val preferences =
            preferencesOf(
                cookieKey to "session-cookie",
                apiKey to "provider-secret",
                themeKey to "DARK",
            ).toMutablePreferences()

        preferences.removeSensitiveBackupPreferences()

        assertNull(preferences[cookieKey])
        assertNull(preferences[apiKey])
        assertEquals("DARK", preferences[themeKey])
    }
}
