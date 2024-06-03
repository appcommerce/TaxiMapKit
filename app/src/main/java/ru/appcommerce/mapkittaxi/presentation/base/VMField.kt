package ru.appcommerce.mapkittaxi.presentation.base

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class VMField<T>(private val strategy: Strategy = Strategy.LAST) : ObservableVMField<T>,
    CurrentVMField<T> {

    enum class Strategy {
        ONCE,
        LAST
    }

    @Volatile
    private var value: T? = null

    @Volatile
    private var wasHandled = false

    private val handler = Handler(Looper.getMainLooper())
    private val data = MutableLiveData<T>()
    private var observer: Observer<T>? = null

    override fun observe(lifecycleOwner: LifecycleOwner, onData: (T) -> Unit) {
        unsubscribe()

        // устанавливаем актуальное значение в LiveData, т.к. до повторного подключения к ObservableVMField,
        // значение (value) могло поменяться (путём вызова mutate())
        value?.run { data.value = this }

        observer = Observer<T> { t ->
            when (strategy) {
                Strategy.ONCE -> {
                    if (!wasHandled) {
                        wasHandled = true
                        onData(t)
                    }
                }
                Strategy.LAST -> onData(t)
            }
        }.also {
            data.observe(lifecycleOwner, it)
        }
    }

    @Synchronized
    fun push(value: T) {
        value?.run {
            this@VMField.value = value
            wasHandled = false
            handler.post { data.value = this }
        }
    }

    @Synchronized
    fun incrementPush(currentValue: T, mutator: (T) -> T) {
        if (hasValue()) {
            mutateAndPush(mutator)
        } else {
            push(currentValue)
        }
    }

    @Synchronized
    fun mutateAndPush(mutator: (T) -> T) {
        value?.run { push(mutator(this)) }
    }

    @Synchronized
    fun mutate(mutator: (T) -> T) {
        value?.run { this@VMField.value = mutator(this) }
    }

    fun withCurrentValue(accept: T.() -> Unit) {
        value?.run { accept(this) }
    }

    fun hasValue() = value != null

    fun clearValue() {
        value = null
    }

    override fun currentValue() = value

    private fun unsubscribe() {
        observer?.run { data.removeObserver(this) }
    }
}
