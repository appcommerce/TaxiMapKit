package ru.appcommerce.mapkittaxi.presentation.feature.main

import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.core.qualifier.qualifier
import ru.appcommerce.mapkittaxi.R
import ru.appcommerce.mapkittaxi.databinding.ActivityMainBinding
import ru.appcommerce.mapkittaxi.di.RouterType
import ru.appcommerce.mapkittaxi.presentation.base.BaseActivity
import ru.appcommerce.mapkittaxi.presentation.states.UiState

class MainActivity : BaseActivity<MainViewModel>() {

    override val viewModel: MainViewModel by viewModel()
    override val binding: ActivityMainBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    private val navigator: AppNavigator by inject(qualifier = qualifier(RouterType.GLOBAL)) {
        parametersOf(
            this,
            R.id.main_container,
            supportFragmentManager,
            supportFragmentManager.fragmentFactory
        )
    }
    private val navigatorHolder: NavigatorHolder by inject(qualifier = qualifier(RouterType.GLOBAL))

    override fun processState(state: UiState) {
        when(state) {
            is MainUIModel.Permission -> processPermissions(state.permissions)
            else -> super.processState(state)
        }
    }

    private fun processPermissions(permissions: Set<String>) {
        val isAllGranted = checkPermissionsGranted(permissions)
        viewModel.showFragment(isAllGranted)
    }

    private fun checkPermissionsGranted(permissions: Set<String>): Boolean {
        return permissions.all { permission ->
            ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}