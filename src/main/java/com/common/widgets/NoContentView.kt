package com.common.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat

class NoContentView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        setBackgroundResource(android.R.color.white)
        orientation = VERTICAL
        gravity = Gravity.CENTER
        context.theme.obtainStyledAttributes(attrs, R.styleable.NoContentView, 0, 0)
            .apply {
                try {
                    addView(TextView(context).apply {
                        gravity = Gravity.CENTER
                        text = getString(R.styleable.NoContentView_android_text)
                        setTextColor(
                            ContextCompat.getColor(
                                context,
                                getResourceId(
                                    R.styleable.NoContentView_android_textColor,
                                    android.R.color.black
                                )
                            )
                        )
                        setTextSize(
                            TypedValue.COMPLEX_UNIT_PX,
                            getDimensionPixelSize(
                                R.styleable.NoContentView_android_textSize,
                                24
                            ).toFloat()
                        )
                    })
                } finally {
                    recycle()
                }
            }
    }
}