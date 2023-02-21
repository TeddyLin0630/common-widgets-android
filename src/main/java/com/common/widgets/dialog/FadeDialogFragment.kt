package com.common.widgets.dialog

import android.os.Bundle
import com.common.widgets.R

open class FadeDialogFragment(
    darkStatusBarIcon: Boolean = false,
    private val overrideTheme: Int = R.style.Theme_Wallet_Fullscreen_DialogFragment
) : BaseDialogFragment(darkStatusBarIcon) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, overrideTheme)
    }
}