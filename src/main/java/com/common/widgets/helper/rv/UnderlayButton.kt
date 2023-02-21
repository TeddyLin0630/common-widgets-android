package com.common.widgets.helper.rv

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint

class UnderlayButton(
    private val text: String,
    private val imageResId: Drawable?,
    private val buttonBackgroundColor: Int,
    private val textColor: Int,
    private val animate: Boolean,
    private val clickListener: UnderlayButtonClickListener
) {
    private var pos = 0
    private var clickRegion: RectF? = null
    fun onClick(x: Float, y: Float): Boolean {
        if (clickRegion != null && clickRegion!!.contains(x, y)) {
            clickListener.onClick(pos)
            return true
        }
        return false
    }

    fun onDraw(canvas: Canvas, rect: RectF, pos: Int) {
        val p = Paint()
        p.color = buttonBackgroundColor
        canvas.drawRect(rect, p)
        if (!animate) {
            p.color = textColor
            p.textSize = 40f
            val r = Rect()
            val cHeight = rect.height()
            val cWidth = rect.width()
            p.textAlign = Paint.Align.LEFT
            p.getTextBounds(text, 0, text.length, r)
            val x = cWidth / 2f - r.width() / 2f - r.left
            val y = cHeight / 2f + r.height() / 2f - r.bottom - 40
            canvas.drawText(text, rect.left + x, rect.top + y, p)
            if (imageResId != null) {
                imageResId.setBounds(
                    (rect.left + 50).toInt(),
                    (rect.top + cHeight / 2f).toInt(),
                    (rect.right - 50).toInt(),
                    (rect.bottom - cHeight / 10f).toInt()
                )
                imageResId.draw(canvas)
            }
        } else {
            val textPaint = TextPaint()
            textPaint.textSize = 40f
            textPaint.color = textColor
            val sl = StaticLayout(
                text, textPaint, rect.width().toInt(),
                Layout.Alignment.ALIGN_CENTER, 1F, 1F, false
            )
            if (imageResId != null) {
                imageResId.setBounds(
                    (rect.left + 50).toInt(),
                    (rect.top + rect.height() / 2f).toInt(),
                    (rect.right - 50).toInt(),
                    (rect.bottom - rect.height() / 10f).toInt()
                )
                imageResId.draw(canvas)
            }
            canvas.save()
            val r = Rect()
            val y = rect.height() / 2f + r.height() / 2f - r.bottom - sl.height / 2
            if (imageResId == null) canvas.translate(
                rect.left,
                rect.top + y
            ) else canvas.translate(rect.left, rect.top + y - 30)
            sl.draw(canvas)
            canvas.restore()
        }
        clickRegion = rect
        this.pos = pos
    }
}

