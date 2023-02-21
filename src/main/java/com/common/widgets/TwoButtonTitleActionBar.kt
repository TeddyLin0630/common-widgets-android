package com.common.widgets

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.ImageViewCompat
import com.common.widgets.databinding.LayoutTwoBtnTitleActionbarBinding

class TwoButtonTitleActionBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding =
        LayoutTwoBtnTitleActionbarBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.TwoButtonTitleActionBar, 0, 0)
            .apply {
                try {
                    binding.leftButton.isInvisible =
                        !getBoolean(R.styleable.TwoButtonTitleActionBar_leftBtnVisible, true)
                    binding.rightButton.isInvisible =
                        !getBoolean(R.styleable.TwoButtonTitleActionBar_rightBtnVisible, true)
                    if (hasValue(R.styleable.TwoButtonTitleActionBar_centerSrc)) {
                        binding.centerIcon.isVisible = true
                        binding.centerIcon.setImageResource(
                            getResourceId(
                                R.styleable.TwoButtonTitleActionBar_centerSrc,
                                android.R.color.white
                            )
                        )
                    }

                    if (hasValue(R.styleable.TwoButtonTitleActionBar_leftBtnSrc)) {
                        getResourceId(R.styleable.TwoButtonTitleActionBar_leftBtnSrc, 0).let {
                            if (it > 0) {
                                binding.leftButton.setImageResource(it)
                            }
                        }
                    }

                    if (hasValue(R.styleable.TwoButtonTitleActionBar_rightBtnSrc)) {
                        getResourceId(R.styleable.TwoButtonTitleActionBar_rightBtnSrc, 0).let {
                            if (it > 0) {
                                binding.rightButton.setImageResource(it)
                            }
                        }
                    }

                    if (hasValue(R.styleable.TwoButtonTitleActionBar_srcTint)) {
                        listOf(
                            binding.centerIcon,
                            binding.rightButton,
                            binding.leftButton
                        ).forEach {
                            ImageViewCompat.setImageTintList(
                                it,
                                ColorStateList.valueOf(
                                    ContextCompat.getColor(
                                        context, getResourceId(
                                            R.styleable.TwoButtonTitleActionBar_srcTint,
                                            android.R.color.white
                                        )
                                    )
                                )
                            )
                        }
                    }

                    binding.title.text =
                        getString(R.styleable.TwoButtonTitleActionBar_actionBarTitle)

                    if (hasValue(R.styleable.TwoButtonTitleActionBar_titleTextColor)) {
                        binding.title.setTextColor(
                            ContextCompat.getColor(
                                context,
                                getResourceId(
                                    R.styleable.TwoButtonTitleActionBar_titleTextColor,
                                    android.R.color.white
                                )
                            )
                        )
                    }

                    if (hasValue(R.styleable.TwoButtonTitleActionBar_containerBg)) {
                        binding.actionBarContainer.setBackgroundResource(
                            getResourceId(
                                R.styleable.TwoButtonTitleActionBar_containerBg,
                                android.R.color.white
                            )
                        )
                    }

                    if (hasValue(R.styleable.TwoButtonTitleActionBar_titleVisible)) {
                        binding.title.isVisible =
                            getBoolean(R.styleable.TwoButtonTitleActionBar_titleVisible, true)
                    }
                } finally {
                    recycle()
                }
            }
    }

    fun setLeftIconVisibility(visibility: Int) {
        binding.leftButton.visibility = visibility
    }

    fun setTitle(title: String?) {
        binding.title.text = title
    }

    fun setLeftOnClickListener(listener: OnClickListener) {
        binding.leftButton.setOnClickListener(listener)
    }

    fun setRightOnClickListener(listener: OnClickListener) {
        binding.rightButton.setOnClickListener(listener)
    }

    fun setCenterIconVisibility(visibility: Int) {
        binding.centerIcon.visibility = visibility
    }

    fun setTitleVisibility(visibility: Int) {
        binding.title.visibility = visibility
    }
}