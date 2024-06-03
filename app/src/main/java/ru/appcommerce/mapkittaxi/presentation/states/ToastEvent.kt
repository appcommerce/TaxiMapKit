package ru.appcommerce.mapkittaxi.presentation.states

data class ToastEvent(val message: String) : UiEvent {
    override val retry: Boolean = false
}
