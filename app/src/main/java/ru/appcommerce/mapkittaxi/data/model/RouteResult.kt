package ru.appcommerce.mapkittaxi.data.model

import com.yandex.mapkit.navigation.RoutePosition

sealed interface RouteResult {
    data class Success(val routes: List<RoutePosition>) : RouteResult
    data class Error(val message: String) : RouteResult
}