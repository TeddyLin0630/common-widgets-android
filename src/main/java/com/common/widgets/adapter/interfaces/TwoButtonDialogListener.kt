package com.common.widgets.adapter.interfaces

import androidx.fragment.app.DialogFragment

interface TwoButtonDialogListener {
    fun onLeftClicked(dialogFragment: DialogFragment)
    fun onRightClicked(dialogFragment: DialogFragment)
}