package ru.appcommerce.mapkittaxi.presentation.base

import androidx.annotation.MainThread

interface CurrentVMField<T> {
    @MainThread
    fun currentValue(): T?
}
