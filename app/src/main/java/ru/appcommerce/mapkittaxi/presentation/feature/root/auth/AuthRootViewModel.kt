package ru.appcommerce.mapkittaxi.presentation.feature.root.auth

import com.github.terrakok.cicerone.Router
import ru.appcommerce.mapkittaxi.navigation.Screens
import ru.appcommerce.mapkittaxi.presentation.base.BaseViewModel

class AuthRootViewModel(
    private val router: Router
) : BaseViewModel() {

    override fun start() {
        router.newRootScreen(Screens.mapScreen)
    }

}