package com.common.widgets.transformer

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class CustomMarginOffsetPagerTransformer(
    private val scale: Boolean = false,
    private val pageMargin: Float,
    private val pageOffset: Float
) : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val offset: Float = position * -(1 * pageOffset + pageMargin)
        when {
            position < -1 -> {
                page.translationX = -offset
            }
            position <= 1 -> {
                if (scale) {
                    val scaleFactor = 0.7f.coerceAtLeast(1 - abs(position - 0.14285715f))
                    page.scaleY = scaleFactor
                    page.alpha = scaleFactor
                }
                page.translationX = offset
            }
            else -> {
                if (scale) {
                    page.alpha = 0.toFloat()
                }
                page.translationX = offset
            }
        }
    }
}