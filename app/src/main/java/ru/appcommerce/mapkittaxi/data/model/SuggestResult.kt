package ru.appcommerce.mapkittaxi.data.model

import com.yandex.mapkit.search.SuggestItem

sealed interface SuggestResult {
    data class Success(val items: List<SuggestItem>) : SuggestResult
    data class Error(val message: String) : SuggestResult
}