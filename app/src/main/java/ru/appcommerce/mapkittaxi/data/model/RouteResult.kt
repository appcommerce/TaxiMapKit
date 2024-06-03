package ru.appcommerce.mapkittaxi.data.model

import com.yandex.mapkit.geometry.Polyline

sealed interface RouteResult {
    data class Success(val routes: List<Polyline>) : RouteResult
    data class Error(val message: String) : RouteResult
}