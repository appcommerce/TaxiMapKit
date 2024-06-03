package ru.appcommerce.mapkittaxi.presentation.feature.main

import ru.appcommerce.mapkittaxi.presentation.states.UiState

sealed interface MainUIModel {

    data class Permission(
        val permissions: Set<String>
    ) : MainUIModel, UiState

}