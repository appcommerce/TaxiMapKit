package ru.appcommerce.mapkittaxi.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import ru.appcommerce.mapkittaxi.presentation.states.UiEvent
import ru.appcommerce.mapkittaxi.presentation.states.UiState

open class BaseViewModel : ViewModel() {

    private val _uiState: VMField<UiState> = VMField()
    val uiState: ObservableVMField<UiState>
        get() = _uiState

    private val _uiEvent: VMField<UiEvent> = VMField(strategy = VMField.Strategy.ONCE)
    val uiEvent: ObservableVMField<UiEvent>
        get() = _uiEvent

    fun pushState(uiState: UiState) {
        _uiState.push(uiState)
    }

    fun pushEvent(uiEvent: UiEvent) {
        _uiEvent.push(uiEvent)
    }

    open fun start() = Unit

    override fun onCleared() {
        super.onCleared()
        if (viewModelScope.isActive) {
            viewModelScope.cancel()
        }
    }
}