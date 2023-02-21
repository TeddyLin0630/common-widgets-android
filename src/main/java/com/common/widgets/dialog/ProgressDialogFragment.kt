package com.common.widgets.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.common.widgets.R
import com.common.widgets.databinding.FragmentProgressDialogBinding

class ProgressDialogFragment : BaseDialogFragment() {
    private var _binding: FragmentProgressDialogBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.Theme_Wallet_Fullscreen_ProgressDialogFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentProgressDialogBinding
        .inflate(inflater, container, false)
        .also { _binding = it }.root

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun isShowing() = dialog?.isShowing ?: false

    companion object {
        fun newInstance(cancelable: Boolean = true) =
            ProgressDialogFragment().apply {
                isCancelable = cancelable
            }
    }
}