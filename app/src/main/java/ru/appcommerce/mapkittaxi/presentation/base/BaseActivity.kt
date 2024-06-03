package ru.appcommerce.mapkittaxi.presentation.base

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding
import com.yandex.mapkit.MapKitFactory
import ru.appcommerce.mapkittaxi.R
import ru.appcommerce.mapkittaxi.presentation.states.UiEvent
import ru.appcommerce.mapkittaxi.presentation.states.UiState

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    abstract val viewModel: VM
    abstract val binding: ViewBinding

    open fun processState(state: UiState) = Unit
    open fun processEvent(event: UiEvent) = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        MapKitFactory.initialize(this)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel.uiState.observe(this, ::processState)
        viewModel.uiEvent.observe(this, ::processEvent)
        viewModel.start()
    }

}