package com.common.widgets.dialog.data

import androidx.annotation.IntDef

const val ACTION_BACK = 0
const val ACTION_CONFIRM = 1
const val ACTION_DECLINE = 2

@IntDef(ACTION_BACK, ACTION_CONFIRM, ACTION_DECLINE)
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
annotation class DialogAction