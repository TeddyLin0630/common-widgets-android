package com.common.widgets.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.common.extensions.delayRun
import com.common.widgets.databinding.FragmentCompletionDialogFragmentBinding
import com.common.widgets.dialog.data.ACTION_CONFIRM
import com.common.widgets.dialog.interfaces.DialogFragmentListener

class CompletionDialogFragment :
    ScaleDialogFragment(darkStatusBarIcon = false) {
    private lateinit var binding: FragmentCompletionDialogFragmentBinding
    private var autoAction: ((DialogFragment) -> Unit)? = null
    private var dialogListener: DialogFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompletionDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            actionButton.text = arguments?.getString(BUNDLE_BUTTON_TEXT)
            description.text = arguments?.getString(BUNDLE_DESCRIPTION)
            title.text = arguments?.getString(BUNDLE_TITLE)
            icon.setImageResource(arguments?.getInt(BUNDLE_ICON) ?: 0)
            actionButton.setOnClickListener {
                dialogListener?.onAction(this@CompletionDialogFragment, ACTION_CONFIRM)
            }
        }
        autoAction?.let { delayRun(AUTO_DISMISS_LENGTH, it) }
    }

    companion object {
        private const val BUNDLE_ICON = "KEY_ICON"
        private const val BUNDLE_TITLE = "KEY_TITLE"
        private const val BUNDLE_DESCRIPTION = "KEY_DESCRIPTION"
        private const val BUNDLE_BUTTON_TEXT = "KEY_BUTTON_TEXT"
        private const val AUTO_DISMISS_LENGTH = 10000L
        fun newInstance(
            @DrawableRes layoutID: Int,
            title: String,
            description: String,
            buttonText: String,
            autoAction: ((DialogFragment) -> Unit)? = null,
            listener: DialogFragmentListener? = null
        ) = CompletionDialogFragment().apply {
            arguments = bundleOf(
                BUNDLE_ICON to layoutID,
                BUNDLE_TITLE to title,
                BUNDLE_DESCRIPTION to description,
                BUNDLE_BUTTON_TEXT to buttonText
            )
            dialogListener = listener
            this.autoAction = autoAction
            isCancelable = false
        }
    }
}