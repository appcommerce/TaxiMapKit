package ru.appcommerce.mapkittaxi.presentation.feature.map

import androidx.lifecycle.viewModelScope
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.search.SuggestItem
import kotlinx.coroutines.launch
import ru.appcommerce.mapkittaxi.data.datasource.RouteRemoteDatasource
import ru.appcommerce.mapkittaxi.data.datasource.SuggestionsRemoteDatasource
import ru.appcommerce.mapkittaxi.data.model.RouteResult
import ru.appcommerce.mapkittaxi.data.model.SuggestResult
import ru.appcommerce.mapkittaxi.presentation.base.BaseViewModel

class MapViewModel(
    private val routeRemoteDatasource: RouteRemoteDatasource,
    private val suggestionsRemoteDatasource: SuggestionsRemoteDatasource
) : BaseViewModel() {

    override fun start() {
        routeRemoteDatasource.route.observeForever { result ->
            when(result) {
                is RouteResult.Success -> pushState(
                    MapUiModel.PolylineGeo(result.routes)
                )
                is RouteResult.Error -> pushEvent(
                    MapUiModel.ErrorResult(result.message)
                )
            }
        }
        suggestionsRemoteDatasource.suggests.observeForever { result ->
            when(result) {
                is SuggestResult.Success -> pushState(
                    MapUiModel.SuggestItems(result.items)
                )
                is SuggestResult.Error -> pushEvent(
                    MapUiModel.ErrorResult(result.message)
                )
            }
        }
    }

    fun onTapAddress(item: SuggestItem) {
        viewModelScope.launch {
            item.center?.let { point ->
                routeRemoteDatasource.requestRoutes(point)
            }
        }
    }

    fun suggest(query: String, bottomLeft: Point, topRight: Point) {
        viewModelScope.launch {
            suggestionsRemoteDatasource.suggest(query, bottomLeft, topRight)
        }
    }
}