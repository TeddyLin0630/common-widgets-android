package com.common.widgets.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.common.widgets.R
import com.common.widgets.adapter.interfaces.TwoButtonDialogListener
import com.common.widgets.databinding.FragmentTwoButtonDialogBinding

/*should be implement base dialog fragment after other commit was merged*/
class TwoButtonSelectionDialog : DialogFragment() {
    private var dialogListener: TwoButtonDialogListener? = null
    private var _binding: FragmentTwoButtonDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTwoButtonDialogBinding.inflate(inflater, container, false)
        val view = binding
        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            leftBtn.setOnClickListener { dialogListener?.onLeftClicked(this@TwoButtonSelectionDialog) }
            rightBtn.setOnClickListener { dialogListener?.onRightClicked(this@TwoButtonSelectionDialog) }
            title.text = arguments?.getString(BUNDLE_TITLE)
            content.text = arguments?.getString(BUNDLE_CONTENT)
            icon.setBackgroundResource(arguments?.getInt(BUNDLE_ICON) ?: 0)
            leftBtn.text = arguments?.getString(BUNDLE_LEFT_BTN_TEXT)
            rightBtn.text = arguments?.getString(BUNDLE_RIGHT_BTN_TEXT)
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
        private const val BUNDLE_LEFT_BTN_TEXT = "BUNDLE_LEFT_BTN_TEXT"
        private const val BUNDLE_RIGHT_BTN_TEXT = "BUNDLE_RIGHT_BTN_TEXT"

        fun newInstance(
            @DrawableRes icon: Int,
            title: String,
            content: String,
            leftBtnText: String,
            rightBtnText: String,
            cancelable: Boolean = false,
            listener: TwoButtonDialogListener? = null
        ) = TwoButtonSelectionDialog().apply {
            arguments = bundleOf(
                BUNDLE_ICON to icon,
                BUNDLE_TITLE to title,
                BUNDLE_CONTENT to content,
                BUNDLE_LEFT_BTN_TEXT to leftBtnText,
                BUNDLE_RIGHT_BTN_TEXT to rightBtnText
            )
            dialogListener = listener
            setStyle(STYLE_NORMAL, R.style.CustomDialogFragment)
            isCancelable = cancelable
        }
    }
}