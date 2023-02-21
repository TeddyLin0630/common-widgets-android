package com.common.widgets.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.common.widgets.R
import com.common.widgets.adapter.interfaces.OneButtonDialogListener
import com.common.widgets.databinding.FragmentOneButtonDialogBinding

class OneButtonSelectionDialog : DialogFragment() {
    private var dialogListener: OneButtonDialogListener? = null
    private var _binding: FragmentOneButtonDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOneButtonDialogBinding.inflate(inflater, container, false)
        val view = binding
        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            actionBtn.setOnClickListener { dialogListener?.onClicked(this@OneButtonSelectionDialog) }
            title.text = arguments?.getString(BUNDLE_TITLE)
            content.text = arguments?.getString(BUNDLE_CONTENT)
            val iconRes = arguments?.getInt(BUNDLE_ICON) ?: 0
            arguments?.getInt(BUNDLE_ICON)?.also {
                if (iconRes > 0) {
                    icon.isVisible = true
                    icon.setBackgroundResource(arguments?.getInt(BUNDLE_ICON) ?: 0)
                }
            }
            actionBtn.text = arguments?.getString(BUNDLE_ACTION_BTN_TEXT)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val BUNDLE_ICON = "BUNDLE_ICON"
        private const val BUNDLE_TITLE = "BUNDLE_TITLE"
        private const val BUNDLE_CONTENT = "BUNDLE_CONTENT"
        private const val BUNDLE_ACTION_BTN_TEXT = "BUNDLE_ACTION_BTN_TEXT"

        fun newInstance(
            @DrawableRes icon: Int,
            title: String,
            content: String,
            actionBtnText: String,
            cancelable: Boolean = false,
            listener: OneButtonDialogListener? = null
        ) = OneButtonSelectionDialog().apply {
            arguments = bundleOf(
                BUNDLE_ICON to icon,
                BUNDLE_TITLE to title,
                BUNDLE_CONTENT to content,
                BUNDLE_ACTION_BTN_TEXT to actionBtnText,
            )
            dialogListener = listener
            setStyle(STYLE_NORMAL, R.style.CustomDialogFragment)
            isCancelable = cancelable
        }
    }
}