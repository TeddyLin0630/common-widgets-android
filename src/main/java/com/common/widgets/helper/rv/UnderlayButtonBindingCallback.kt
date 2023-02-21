package com.common.widgets.helper.rv

import androidx.recyclerview.widget.RecyclerView

fun interface UnderlayButtonBindingCallback {
    fun instantiate(
        viewHolder: RecyclerView.ViewHolder?,
        underlayButtons: MutableList<UnderlayButton>
    )
}
