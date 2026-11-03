/**
 * Veritm Project (C) 2026
 * Licensed under GPL-3.0 | See git history for contributors
 */

package com.veritm.music.ui.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import com.veritm.music.R

@Composable
fun VeritmLoadingIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
) {
    val transition = rememberInfiniteTransition(label = "veritm-loading")
    val pulse by
        transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(durationMillis = 900, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse,
                ),
            label = "pulse",
        )

    Box(
        modifier =
            modifier
                .graphicsLayer {
                    val scale = 0.88f + pulse * 0.12f
                    scaleX = scale
                    scaleY = scale
                    alpha = 0.72f + pulse * 0.28f
                }
                .drawBehind {
                    drawCircle(color.copy(alpha = 0.13f), radius = size.minDimension * 0.42f)
                }
                .semantics {
                    progressBarRangeInfo = ProgressBarRangeInfo.Indeterminate
                },
        contentAlignment = androidx.compose.ui.Alignment.Center,
    ) {
        AnimatedVeritmLogo(
            modifier = Modifier.fillMaxSize(),
            contentDescription = stringResource(R.string.veritm_loading_content_description),
        )
    }
}
