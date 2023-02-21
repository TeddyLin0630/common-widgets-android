package com.common.widgets.viewstate

sealed class DefaultViewState {
    object None : DefaultViewState()
    data class Error(val reason: String) : DefaultViewState()
    data class Failure(val errorMsg: String) : DefaultViewState()
    data class Loading(val isLoading: Boolean) : DefaultViewState()
}