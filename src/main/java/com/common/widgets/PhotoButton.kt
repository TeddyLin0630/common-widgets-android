package com.common.widgets

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.common.widgets.databinding.LayoutPhotoButtonBinding

class PhotoButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    val binding = LayoutPhotoButtonBinding.inflate(LayoutInflater.from(context), this)

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomPhotoButton, 0, 0).apply {
            try {
                binding.action.text = getString(R.styleable.CustomPhotoButton_actionText)
            } finally {
                recycle()
            }
        }
    }
}