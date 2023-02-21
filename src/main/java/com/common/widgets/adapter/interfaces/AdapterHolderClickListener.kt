package com.common.widgets.adapter.interfaces

fun interface AdapterHolderClickListener<T> {
    fun onItemClicked(position: Int, data: T)
}