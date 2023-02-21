package com.common.widgets.dialog

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.common.widgets.R
import com.common.widgets.databinding.FragmentCommonDetailContentBinding
import com.common.widgets.decoration.MarginItemDecoration
import com.common.widgets.dialog.adapter.CommonDetailContentAdapter
import com.common.widgets.dialog.data.ACTION_BACK
import com.common.widgets.dialog.data.AllLabelContent
import com.common.widgets.dialog.data.LabelWithContent
import com.common.widgets.dialog.interfaces.DialogFragmentListener

class CommonDetailContentDialogFragment : SlideDialogFragment() {
    private var _binding: FragmentCommonDetailContentBinding? = null
    private val binding get() = _binding
    private var dialogListener: DialogFragmentListener? = null
    private var onBackKeyAction: ((DialogFragment) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentCommonDetailContentBinding.inflate(inflater, container, false)
        .also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            contents.apply {
                adapter = CommonDetailContentAdapter(
                    arguments?.getParcelable<AllLabelContent>(BUNDLE_KEY_DATA)?.contents ?: listOf()
                )
                addItemDecoration(MarginItemDecoration(bottom = resources.getDimensionPixelOffset(R.dimen.standard_margin)))
            }
            actionBar.setTitle(arguments?.getString(BUNDLE_KEY_TITLE))
            actionBar.setLeftOnClickListener {
                dialogListener?.onAction(this@CommonDetailContentDialogFragment, ACTION_BACK)
            }
            arguments?.getString(BUNDLE_KEY_SUBTITLE)?.takeIf { it.isNotEmpty() }?.let {
                subtitle.text = it
                subtitle.visibility = View.VISIBLE
            }
        }

        dialog?.setOnKeyListener { _, code, _ ->
            if (code == KeyEvent.KEYCODE_BACK && onBackKeyAction != null) {
                onBackKeyAction?.invoke(this)
                true
            } else {
                false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialogListener = null
        onBackKeyAction = null
        _binding = null
    }

    companion object {
        private const val BUNDLE_KEY_DATA = "BUNDLE_KEY_CONTENT"
        private const val BUNDLE_KEY_TITLE = "BUNDLE_KEY_TITLE"
        private const val BUNDLE_KEY_SUBTITLE = "BUNDLE_KEY_SUBTITLE"

        fun newInstance(
            title: String,
            subTitle: String = "",
            content: List<LabelWithContent> = listOf(),
            cancelable: Boolean = true,
            backKeyAction: ((DialogFragment) -> Unit)? = null,
            listener: DialogFragmentListener? = null
        ) =
            CommonDetailContentDialogFragment().apply {
                arguments = bundleOf(
                    BUNDLE_KEY_TITLE to title,
                    BUNDLE_KEY_SUBTITLE to subTitle,
                    BUNDLE_KEY_DATA to AllLabelContent(content)
                )
            }.apply {
                isCancelable = cancelable
                dialogListener = listener
                onBackKeyAction = backKeyAction
            }
    }
}