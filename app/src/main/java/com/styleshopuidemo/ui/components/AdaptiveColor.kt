package com.styleshopuidemo.ui.components

import android.content.res.Resources
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.styleshopuidemo.ui.theme.StyleShopColors
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

enum class ImageSampleArea {
    Full,
    TopStart,
    TopEnd,
    Center
}

@Immutable
data class AdaptiveOverlayStyle(
    val tint: Color,
    val surfaceBrush: Brush,
    val borderColor: Color
)

@Composable
fun rememberAdaptiveOverlayStyle(
    @DrawableRes imageRes: Int,
    preferredTint: Color = StyleShopColors.gold,
    sampleArea: ImageSampleArea = ImageSampleArea.TopEnd
): AdaptiveOverlayStyle {
    val resources = LocalContext.current.resources
    return remember(imageRes, preferredTint, sampleArea) {
        val sampledColor = Color(averageDrawableColor(resources, imageRes, sampleArea))
        val preferredContrast = contrastRatio(preferredTint, sampledColor)
        val whiteContrast = contrastRatio(Color.White, sampledColor)
        val darkContrast = contrastRatio(StyleShopColors.textPrimary, sampledColor)
        val useDark = sampledColor.luminance() > 0.72f && darkContrast > whiteContrast
        val tint = when {
            preferredContrast >= 2.25f -> preferredTint
            useDark -> StyleShopColors.textPrimary
            else -> Color.White
        }
        val needsDarkSurface = tint == Color.White
        AdaptiveOverlayStyle(
            tint = tint,
            surfaceBrush = if (needsDarkSurface) {
                Brush.linearGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.20f),
                        Color.Black.copy(alpha = 0.10f),
                        Color.White.copy(alpha = 0.03f)
                    )
                )
            } else {
                Brush.linearGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.24f),
                        Color.White.copy(alpha = 0.11f),
                        StyleShopColors.goldSoft.copy(alpha = 0.05f),
                        Color.White.copy(alpha = 0.06f)
                    )
                )
            },
            borderColor = if (needsDarkSurface) {
                Color.White.copy(alpha = 0.48f)
            } else {
                Color.White.copy(alpha = 0.60f)
            }
        )
    }
}

private fun averageDrawableColor(
    resources: Resources,
    @DrawableRes resId: Int,
    sampleArea: ImageSampleArea
): Int {
    val cacheKey = "$resId:$sampleArea"
    synchronized(adaptiveColorCache) {
        adaptiveColorCache[cacheKey]?.let { return it }
    }

    val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
    BitmapFactory.decodeResource(resources, resId, bounds)
    val decodeOptions = BitmapFactory.Options().apply {
        inSampleSize = calculateInSampleSize(bounds, 64, 64)
        inPreferredConfig = android.graphics.Bitmap.Config.ARGB_8888
    }
    val bitmap = BitmapFactory.decodeResource(resources, resId, decodeOptions)
        ?: return Color.White.toArgb()

    var red = 0L
    var green = 0L
    var blue = 0L
    var count = 0L
    val area = sampleBounds(bitmap.width, bitmap.height, sampleArea)
    val step = max(1, min(bitmap.width, bitmap.height) / 24)

    for (y in area.top until area.bottom step step) {
        for (x in area.left until area.right step step) {
            val pixel = bitmap.getPixel(x, y)
            val alpha = android.graphics.Color.alpha(pixel)
            if (alpha > 24) {
                red += android.graphics.Color.red(pixel)
                green += android.graphics.Color.green(pixel)
                blue += android.graphics.Color.blue(pixel)
                count++
            }
        }
    }
    bitmap.recycle()

    val result = if (count == 0L) {
        Color.White.toArgb()
    } else {
        android.graphics.Color.rgb(
            (red / count).toInt(),
            (green / count).toInt(),
            (blue / count).toInt()
        )
    }
    synchronized(adaptiveColorCache) {
        adaptiveColorCache[cacheKey] = result
    }
    return result
}

private val adaptiveColorCache = mutableMapOf<String, Int>()

private data class SampleBounds(
    val left: Int,
    val top: Int,
    val right: Int,
    val bottom: Int
)

private fun sampleBounds(width: Int, height: Int, sampleArea: ImageSampleArea): SampleBounds {
    val thirdW = width / 3
    val thirdH = height / 3
    return when (sampleArea) {
        ImageSampleArea.Full -> SampleBounds(0, 0, width, height)
        ImageSampleArea.TopStart -> SampleBounds(0, 0, thirdW * 2, thirdH * 2)
        ImageSampleArea.TopEnd -> SampleBounds(thirdW, 0, width, thirdH * 2)
        ImageSampleArea.Center -> SampleBounds(width / 4, height / 4, width * 3 / 4, height * 3 / 4)
    }
}

private fun calculateInSampleSize(
    options: BitmapFactory.Options,
    reqWidth: Int,
    reqHeight: Int
): Int {
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1
    if (height > reqHeight || width > reqWidth) {
        var halfHeight = height / 2
        var halfWidth = width / 2
        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
            halfHeight /= 2
            halfWidth /= 2
        }
    }
    return max(1, inSampleSize)
}

private fun contrastRatio(foreground: Color, background: Color): Float {
    val first = relativeLuminance(foreground) + 0.05f
    val second = relativeLuminance(background) + 0.05f
    return max(first, second) / min(first, second)
}

private fun relativeLuminance(color: Color): Float {
    fun channel(value: Float): Float {
        return if (value <= 0.03928f) value / 12.92f else ((value + 0.055f) / 1.055f).pow(2.4f)
    }
    return 0.2126f * channel(color.red) +
        0.7152f * channel(color.green) +
        0.0722f * channel(color.blue)
}
