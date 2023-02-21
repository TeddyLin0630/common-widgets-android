package com.common.widgets.dialog.interfaces

import androidx.fragment.app.DialogFragment
import com.common.widgets.dialog.data.DialogAction

fun interface DialogFragmentListener {
    fun onAction(dialogFragment: DialogFragment, @DialogAction action: Int)
}