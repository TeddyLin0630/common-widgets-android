package com.common.widgets

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.*

class SpinnerDatePickerFragment(
    private val listener: DatePickerDialog.OnDateSetListener,
    private val dismissAction: () -> Unit,
    private val defaultDateString: String? = null
) : DialogFragment() {
    @SuppressLint("SimpleDateFormat")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val defaultDate: Date? = defaultDateString?.let {
            try {
                SimpleDateFormat("yyyy-MM-dd").parse(it)
            } catch (e: Exception) {
                null
            }
        }
        val calendar = Calendar.getInstance().apply {
            if (defaultDate != null) {
                time = defaultDate
            }
        }
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        return DatePickerDialog(
            requireActivity(),
            AlertDialog.THEME_HOLO_LIGHT, listener, year, month, day
        ).apply {
            this.datePicker.maxDate = Calendar.getInstance().timeInMillis
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissAction()
    }
}