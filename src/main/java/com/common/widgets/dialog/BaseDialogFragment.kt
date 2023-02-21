package com.common.widgets.dialog

import android.graphics.Color
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.DialogFragment

open class BaseDialogFragment(private val darkStatusBarIcon: Boolean = false) : DialogFragment() {
    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            decorView.systemUiVisibility =
                if (darkStatusBarIcon) {
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                }
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
        }
    }
}