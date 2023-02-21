package com.common.widgets.viewstate

sealed class DataViewState<out T> {
    object None : DataViewState<Nothing>()
    data class Success<D>(val data: D) : DataViewState<D>()
    data class Error(val reason: String) : DataViewState<Nothing>()
    data class Failure(val errorMsg: String) : DataViewState<Nothing>()
    data class Loading(val isLoading: Boolean) : DataViewState<Nothing>()
}