package com.veritm.music.utils

import androidx.datastore.preferences.core.MutablePreferences
import com.veritm.music.constants.AccountChannelHandleKey
import com.veritm.music.constants.AccountEmailKey
import com.veritm.music.constants.AccountNameKey
import com.veritm.music.constants.DataSyncIdKey
import com.veritm.music.constants.DeeplApiKey
import com.veritm.music.constants.InnerTubeAuthUserKey
import com.veritm.music.constants.InnerTubeCookieKey
import com.veritm.music.constants.LastFMUsernameKey
import com.veritm.music.constants.LastFMSessionKey
import com.veritm.music.constants.ListenTogetherBlockedUsersKey
import com.veritm.music.constants.ListenTogetherIsHostKey
import com.veritm.music.constants.ListenTogetherRoomCodeKey
import com.veritm.music.constants.ListenTogetherServerUrlKey
import com.veritm.music.constants.ListenTogetherSessionTimestampKey
import com.veritm.music.constants.ListenTogetherSessionTokenKey
import com.veritm.music.constants.ListenTogetherUserIdKey
import com.veritm.music.constants.ListenTogetherUsernameKey
import com.veritm.music.constants.OpenRouterApiKey
import com.veritm.music.constants.OpenRouterBaseUrlKey
import com.veritm.music.constants.ProxyPasswordKey
import com.veritm.music.constants.ProxyUrlKey
import com.veritm.music.constants.ProxyUsernameKey
import com.veritm.music.constants.VisitorDataKey

internal fun MutablePreferences.removeSensitiveBackupPreferences() {
    remove(InnerTubeCookieKey)
    remove(VisitorDataKey)
    remove(DataSyncIdKey)
    remove(InnerTubeAuthUserKey)
    remove(AccountNameKey)
    remove(AccountEmailKey)
    remove(AccountChannelHandleKey)
    remove(ListenTogetherServerUrlKey)
    remove(ListenTogetherUsernameKey)
    remove(ListenTogetherBlockedUsersKey)
    remove(ListenTogetherSessionTokenKey)
    remove(ListenTogetherRoomCodeKey)
    remove(ListenTogetherUserIdKey)
    remove(ListenTogetherIsHostKey)
    remove(ListenTogetherSessionTimestampKey)
    remove(LastFMSessionKey)
    remove(LastFMUsernameKey)
    remove(OpenRouterApiKey)
    remove(OpenRouterBaseUrlKey)
    remove(DeeplApiKey)
    remove(ProxyUrlKey)
    remove(ProxyUsernameKey)
    remove(ProxyPasswordKey)
}
