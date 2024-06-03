package ru.appcommerce.mapkittaxi.presentation.base

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner

interface ObservableVMField<T> : CurrentVMField<T> {
    @MainThread
    fun observe(lifecycleOwner: LifecycleOwner, onData: (T) -> Unit)
}
