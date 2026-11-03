package com.veritm.music.ui.component

import android.graphics.ImageDecoder
import android.graphics.drawable.AnimatedImageDrawable
import android.os.Build
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.veritm.music.BuildConfig
import com.veritm.music.R
import com.veritm.music.constants.WelcomeSeenVersionKey
import com.veritm.music.constants.UseNewMiniPlayerDesignKey
import com.veritm.music.constants.UseNewPlayerDesignKey
import com.veritm.music.utils.dataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import androidx.datastore.preferences.core.edit

private enum class StartupPhase { SPLASH, WELCOME, HIDDEN }

@Composable
fun AnimatedVeritmLogo(
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        AndroidView(
            modifier = modifier,
            factory = { viewContext ->
                ImageView(viewContext).apply {
                    scaleType = ImageView.ScaleType.FIT_CENTER
                    this.contentDescription = contentDescription
                    val drawable = runCatching {
                        ImageDecoder.decodeDrawable(
                            ImageDecoder.createSource(viewContext.assets, "veritm_logo_animated.webp"),
                        )
                    }.getOrElse {
                        androidx.core.content.ContextCompat.getDrawable(viewContext, R.drawable.veritm_logo_transparent)
                    }
                    setImageDrawable(drawable)
                    (drawable as? AnimatedImageDrawable)?.apply {
                        repeatCount = AnimatedImageDrawable.REPEAT_INFINITE
                        start()
                    }
                }
            },
            update = { view ->
                (view.drawable as? AnimatedImageDrawable)?.let { if (!it.isRunning) it.start() }
            },
            onRelease = { view -> (view.drawable as? AnimatedImageDrawable)?.stop() },
        )
    } else {
        Image(
            painter = painterResource(R.drawable.veritm_logo_transparent),
            contentDescription = contentDescription,
            modifier = modifier,
        )
    }
}

@Composable
fun StartupExperience(onVisibilityChanged: (Boolean) -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var phase by remember { mutableStateOf(StartupPhase.SPLASH) }
    val version = BuildConfig.BASE_VERSION_NAME

    LaunchedEffect(version) {
        val showWelcome =
            runCatching { context.dataStore.data.first()[WelcomeSeenVersionKey] != version }
                .getOrDefault(true)
        delay(1_250)
        if (showWelcome) {
            runCatching {
                context.dataStore.edit { settings ->
                    settings[UseNewMiniPlayerDesignKey] = true
                    settings[UseNewPlayerDesignKey] = true
                }
            }
        }
        phase = if (showWelcome) StartupPhase.WELCOME else StartupPhase.HIDDEN
    }

    LaunchedEffect(phase) {
        onVisibilityChanged(phase != StartupPhase.HIDDEN)
    }

    if (phase == StartupPhase.HIDDEN) return

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF03100D), Color(0xFF08231A), Color(0xFF030D0B)),
                    ),
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {},
                ),
        contentAlignment = Alignment.Center,
    ) {
        if (phase == StartupPhase.SPLASH) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AnimatedVeritmLogo(Modifier.size(144.dp), stringResource(R.string.brand_logo_desc))
                Spacer(Modifier.height(18.dp))
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                    letterSpacing = 7.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(0.84f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(18.dp),
            ) {
                AnimatedVeritmLogo(Modifier.size(152.dp), stringResource(R.string.brand_logo_desc))
                Text(
                    text = stringResource(R.string.welcome_title),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = stringResource(R.string.welcome_body),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(10.dp))
                Button(
                    onClick = {
                        scope.launch {
                            runCatching { context.dataStore.edit { it[WelcomeSeenVersionKey] = version } }
                            phase = StartupPhase.HIDDEN
                        }
                    },
                    modifier = Modifier.width(244.dp).height(54.dp).clip(RoundedCornerShape(28.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                ) {
                    Text(
                        text = stringResource(R.string.welcome_continue),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }
    }
}
