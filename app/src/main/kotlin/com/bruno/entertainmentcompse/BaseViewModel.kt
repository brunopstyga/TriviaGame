package com.bruno.entertainmentcompse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception, "Unexpected coroutine error in ${this::class.simpleName}")
        onError(exception)
    }

    protected fun launchSafe(
        dispatcher: CoroutineDispatcher = defaultDispatcher,
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(dispatcher + exceptionHandler) {
            block()
        }
    }

    protected open fun onError(exception: Throwable) {
    }
}
