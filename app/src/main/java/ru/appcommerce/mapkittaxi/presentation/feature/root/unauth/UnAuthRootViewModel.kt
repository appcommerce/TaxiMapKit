package ru.appcommerce.mapkittaxi.presentation.feature.root.unauth

import com.github.terrakok.cicerone.Router
import ru.appcommerce.mapkittaxi.navigation.Screens
import ru.appcommerce.mapkittaxi.presentation.base.BaseViewModel

class UnAuthRootViewModel(
    private val router: Router
) : BaseViewModel() {

    override fun start() {
        router.newRootScreen(Screens.requestPermissionsScreen)
    }

}