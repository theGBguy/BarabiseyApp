package io.github.thegbguy.barabiseyapp.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min

@Composable
fun ZoomableAsyncImage(
    model: Any?,
    contentDescription: String? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalPlatformContext.current
    val scope = rememberCoroutineScope()
    val scale = remember { Animatable(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    if (scale.value == 1f) return@detectTransformGestures
                    scope.launch {
                        scale.snapTo(max(1f, min(5f, scale.value * zoom))) // Snap zoom
                    }
                    offset = Offset(
                        x = offset.x + pan.x,
                        y = offset.y + pan.y
                    )
                }
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        val targetZoom = if (scale.value > 1f) 1f else 2f
                        scope.launch {
                            scale.animateTo(
                                targetZoom,
                                animationSpec = tween(650)
                            ) // Toggle zoom level (1x or 2x)
                        }
                        if (targetZoom == 1f) {
                            offset = Offset.Zero // Reset offset on zoom out
                        }
                    }
                )
            }
    ) {
        AsyncImage(
            model = model,
            contentDescription = contentDescription,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale.value,
                    scaleY = scale.value,
                    translationX = offset.x,
                    translationY = offset.y
                ),
            imageLoader = ImageLoader(context)
        )
    }
}
