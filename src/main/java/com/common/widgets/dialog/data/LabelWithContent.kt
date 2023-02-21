package com.common.widgets.dialog.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LabelWithContent(val label: String, val content: String) : Parcelable

@Parcelize
data class AllLabelContent(val contents: List<LabelWithContent>) : Parcelable