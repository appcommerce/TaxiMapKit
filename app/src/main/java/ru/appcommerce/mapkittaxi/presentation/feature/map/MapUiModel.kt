package ru.appcommerce.mapkittaxi.presentation.feature.map

import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.search.SuggestItem
import ru.appcommerce.mapkittaxi.presentation.states.UiEvent
import ru.appcommerce.mapkittaxi.presentation.states.UiState

sealed interface MapUiModel {
    data class PolylineGeo(val points: List<Polyline>) : UiState
    data class ErrorResult(val message: String) : UiEvent {
        override val retry: Boolean = false
    }
    data class SuggestItems(val suggestItems: List<SuggestItem>) : UiState

    data class MyLocation(val point: Point) : UiState
}