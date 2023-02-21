package com.common.widgets.dialog

import android.os.Bundle
import com.common.widgets.R

open class ScaleDialogFragment(darkStatusBarIcon: Boolean = false) :
    BaseDialogFragment(darkStatusBarIcon) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.Theme_Wallet_Scale_Fullscreen_DialogFragment)
    }
}