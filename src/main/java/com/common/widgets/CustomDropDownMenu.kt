package com.common.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.common.widgets.databinding.LayoutExposedDropdownMenuBinding

class CustomDropDownMenu @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {
    val binding =
        LayoutExposedDropdownMenuBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.theme.obtainStyledAttributes(attributeSet, R.styleable.CustomInputField, 0, 0)
            .apply {
                try {
                    binding.menuLayout.hint = getString(R.styleable.CustomInputField_android_hint)
                    binding.menu.hint = getString(R.styleable.CustomInputField_exampleText)
                } finally {
                    recycle()
                }
            }
    }

    fun menuItemClickListener(listener: AdapterView.OnItemClickListener) {
        binding.menu.onItemClickListener = listener
    }

    fun setHint(label: String?) {
        binding.menuLayout.hint = label
    }

    fun setText(content: String?) {
        binding.menu.setText(content, false)
    }

    fun text() = binding.menu.text.toString()

    fun isTextEmpty() = binding.menu.text.isNullOrEmpty()

    fun isTextEmptyIfVisible() = if (isVisible) binding.menu.text.isNullOrEmpty() else false

    fun error(text: CharSequence?) {
        binding.menuLayout.error = text
    }

    fun errorIf(condition: Boolean, errorMsg: String): Boolean {
        if (condition) error(errorMsg) else error(null)
        return condition
    }

    fun errorIfNot(condition: Boolean, errorMsg: String) {
        if (!condition) error(errorMsg) else error(null)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun customMenuBehavior(behavior: (view: View) -> Unit) {
        binding.menu.setOnTouchListener { v, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                behavior(v)
            }
            false
        }
        binding.menuLayout.setEndIconOnClickListener { v ->
            behavior(v)
        }
    }

    fun <T> adapter(adapter: ArrayAdapter<T>) {
        (binding.menu as? AutoCompleteTextView)?.setAdapter(adapter)
    }
}