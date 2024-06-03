package ru.appcommerce.mapkittaxi.data.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yandex.mapkit.geometry.BoundingBox
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.search.SearchFactory
import com.yandex.mapkit.search.SearchManagerType
import com.yandex.mapkit.search.SuggestOptions
import com.yandex.mapkit.search.SuggestResponse
import com.yandex.mapkit.search.SuggestSession.SuggestListener
import com.yandex.runtime.Error
import ru.appcommerce.mapkittaxi.data.model.SuggestResult

class SuggestionsRemoteDatasource {

    private val searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED)
    private val suggestSession = searchManager.createSuggestSession()

    private val _suggests: MutableLiveData<SuggestResult> = MutableLiveData()
    val suggests: LiveData<SuggestResult> get() = _suggests

    fun suggest(query: String, bottomLeft: Point, topRight: Point) {
        suggestSession.suggest(
            query,
            BoundingBox(bottomLeft, topRight),
            SuggestOptions(),
            object : SuggestListener {
                override fun onResponse(response: SuggestResponse) {
                    _suggests.postValue(
                        SuggestResult.Success(response.items)
                    )
                }
                override fun onError(error: Error) {
                    //TODO: Сделать нормальный маппинг ошибок
                    _suggests.postValue(
                        SuggestResult.Error("Невозможно обработать запрос")
                    )
                }
            }
        )
    }
}