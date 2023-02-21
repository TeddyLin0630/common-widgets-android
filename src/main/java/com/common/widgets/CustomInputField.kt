package com.common.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.common.widgets.databinding.LayoutInputFieldBinding
import com.google.android.material.textfield.TextInputLayout

class CustomInputField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val binding = LayoutInputFieldBinding.inflate(LayoutInflater.from(context), this)
    val text get() = binding.edit.text

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomInputField, 0, 0).apply {
            try {
                binding.editLayout.apply {
                    prefixText = getString(R.styleable.CustomInputField_prefixText)
                    isErrorEnabled =
                        getBoolean(R.styleable.CustomInputField_errorEnabled, false)
                    hint = getString(R.styleable.CustomInputField_android_hint)
                    endIconMode =
                        getInt(
                            R.styleable.CustomInputField_endIconMode,
                            TextInputLayout.END_ICON_NONE
                        )
                    helperText = getString(R.styleable.CustomInputField_helperText)
                    if (hasValue(R.styleable.CustomInputField_endIconDrawable)) {
                        endIconDrawable = getDrawable(R.styleable.CustomInputField_endIconDrawable)
                    }
                    binding.edit.setSelectAllOnFocus(
                        getBoolean(
                            R.styleable.CustomInputField_android_selectAllOnFocus,
                            true
                        )
                    )
                    if (hasValue(R.styleable.CustomInputField_strokeColor)) {
                        getColorStateList(R.styleable.CustomInputField_strokeColor)?.let {
                            setBoxStrokeColorStateList(it)
                        }
                    }

                    if (hasValue(R.styleable.CustomInputField_endIconTint)) {
                        setEndIconTintList(
                            getColorStateList(R.styleable.CustomInputField_endIconTint)
                        )
                    }
                }

                binding.edit.apply {
                    inputType =
                        getInt(
                            R.styleable.CustomInputField_android_inputType,
                            InputType.TYPE_CLASS_TEXT
                        )
                    imeOptions =
                        getInt(
                            R.styleable.CustomInputField_android_imeOptions,
                            EditorInfo.IME_ACTION_NONE
                        )

                    if (hasValue(R.styleable.CustomInputField_android_maxLength)) {
                        filters += InputFilter.LengthFilter(
                            getInteger(
                                R.styleable.CustomInputField_android_maxLength,
                                999
                            )
                        )
                    }

                    hint = getString(R.styleable.CustomInputField_exampleText)

                    if (hasValue(R.styleable.CustomInputField_onlyAlphabetNumeric) &&
                        getBoolean(R.styleable.CustomInputField_onlyAlphabetNumeric, false)
                    ) {
                        filters += alphabetNumericInputFilter()
                    }

                    if (hasValue(R.styleable.CustomInputField_textAllCaps) &&
                        getBoolean(R.styleable.CustomInputField_textAllCaps, false)
                    ) {
                        filters += InputFilter.AllCaps()
                    }
                }
                getString(R.styleable.CustomInputField_trailingText)?.let {
                    binding.trailingTip.text = it
                    binding.trailingTip.isVisible = true
                }

                getString(R.styleable.CustomInputField_customLabelText)?.let {
                    setCustomLabel(it)
                }
            } finally {
                recycle()
            }
        }
    }

    fun prefixText() = binding.editLayout.prefixText?.toString()

    fun hint() = binding.editLayout.hint

    fun setCustomLabel(label: String?) {
        binding.customLabel.text = label
        binding.customLabel.isVisible = true
        binding.editLayout.isHintEnabled = false
    }

    fun setHint(label: String?) {
        binding.editLayout.hint = label
    }

    fun setText(content: String?) {
        binding.edit.setText(content)
    }

    fun isTextEmpty() = binding.edit.text.isNullOrEmpty()

    fun setExample(text: String?) {
        binding.edit.hint = text
    }

    fun setSelection(index: Int) {
        binding.edit.setSelection(index)
    }

    fun focusListener(listener: OnFocusChangeListener) {
        binding.edit.onFocusChangeListener = listener
    }

    fun error(text: CharSequence?) {
        if (text == null) binding.editLayout.isErrorEnabled = false
        binding.editLayout.error = text
    }

    fun errorIf(condition: Boolean, errorMsg: String): Boolean {
        if (condition) error(errorMsg) else error(null)
        return condition
    }

    fun errorIfNot(condition: Boolean, errorMsg: String): Boolean {
        if (!condition) error(errorMsg) else error(null)
        return !condition
    }

    fun addTextChangedListener(listener: TextWatcher) {
        binding.edit.addTextChangedListener(listener)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun touchListener(listener: OnTouchListener) {
        binding.edit.setOnTouchListener(listener)
    }

    fun setOnEditorActionListener(listener: TextView.OnEditorActionListener) {
        binding.edit.setOnEditorActionListener(listener)
    }

    fun addTrailingTipClickListener(listener: OnClickListener) {
        binding.trailingTip.setOnClickListener(listener)
    }

    fun addOnEditTextAttachedListener(listener: TextInputLayout.OnEditTextAttachedListener) {
        binding.editLayout.addOnEditTextAttachedListener(listener)
    }

    fun setEndIconOnClickListener(listener: OnClickListener) {
        binding.editLayout.setEndIconOnClickListener(listener)
    }

    fun activeEndIcon(active: Boolean) {
        binding.editLayout.setEndIconActivated(active)
    }

    fun enableNonZeroAndAutoFill() {
        binding.edit.doOnTextChanged { text, _, _, _ ->
            if (text != null && text.length > 1 && text[0] == '0') {
                binding.edit.setText(text.substring(1, text.length))
                binding.edit.setSelection(binding.edit.text?.length ?: 0)
            } else if (text.isNullOrEmpty()) {
                binding.edit.setText("0")
                binding.edit.setSelection(binding.edit.text?.length ?: 0)
            }
        }
    }

    fun actionDone(action: () -> Unit) {
        binding.edit.imeOptions = EditorInfo.IME_ACTION_DONE
        binding.edit.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE ->
                    action()
            }
            false
        }
    }

    fun isTextEmptyIfVisible(): Boolean =
        if (isVisible) binding.edit.text.isNullOrEmpty() else false

    private fun alphabetNumericInputFilter(): InputFilter {
        return InputFilter { source, _, _, _, _, _ ->
            if (source == "") { // for backspace
                return@InputFilter source
            }
            if (source.toString().matches("[a-zA-Z0-9 ]+".toRegex())) {
                source
            } else ""
        }
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun CustomInputField.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}