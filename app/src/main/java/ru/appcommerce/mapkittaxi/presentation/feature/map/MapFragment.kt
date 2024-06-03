package ru.appcommerce.mapkittaxi.presentation.feature.map

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.appcommerce.mapkittaxi.R
import ru.appcommerce.mapkittaxi.databinding.FragmentMapBinding
import ru.appcommerce.mapkittaxi.presentation.base.BaseFragment
import ru.appcommerce.mapkittaxi.presentation.states.UiEvent
import ru.appcommerce.mapkittaxi.presentation.states.UiState

class MapFragment : BaseFragment<MapViewModel>() {

    override val viewModel: MapViewModel by viewModel()
    override val binding: FragmentMapBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    private val suggestAdapter: SearchAdapter by lazy { SearchAdapter(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.suggestField.apply {
            setAdapter(suggestAdapter)
            setOnItemClickListener { _, _, i, _ ->
                val item = suggestAdapter.getItemByPosition(i) ?: return@setOnItemClickListener
                viewModel.onTapAddress(item)
            }
        }.startSearchEvent()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapview.onStart()
    }

    override fun onStop() {
        binding.mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun processState(state: UiState) {
        when(state) {
            is MapUiModel.SuggestItems -> suggestAdapter.setData(state.suggestItems)
            is MapUiModel.PolylineGeo -> drawPolyline(state.points)
            else -> Unit
        }
    }

    private fun drawPolyline(polylines: List<Polyline>) {
        if (polylines.isEmpty()) return
        polylines.forEach {
            binding.mapview.mapWindow.map.mapObjects.addPolyline(it)
        }
    }

    override fun processEvent(event: UiEvent) {
        when(event) {
            is MapUiModel.ErrorResult -> Toast.makeText(requireActivity().applicationContext, event.message, Toast.LENGTH_LONG).show()
            else -> Unit
        }
    }

    override fun searchAction(searchText: String) {
        super.searchAction(searchText)
        viewModel.suggest(
            query = searchText,
            binding.mapview.mapWindow.map.visibleRegion.bottomLeft,
            binding.mapview.mapWindow.map.visibleRegion.topRight
        )
    }
}