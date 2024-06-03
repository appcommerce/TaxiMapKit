package ru.appcommerce.mapkittaxi.presentation.feature.main

import android.Manifest
import com.github.terrakok.cicerone.Router
import ru.appcommerce.mapkittaxi.navigation.Screens
import ru.appcommerce.mapkittaxi.presentation.base.BaseViewModel

class MainViewModel(
    val router: Router
) : BaseViewModel() {

    override fun start() {
        super.start()
        pushState(
            MainUIModel.Permission(
                setOf(PERMISSION_APPROXIMATE_LOCATION, PERMISSION_PRECISE_LOCATION)
            )
        )
    }

    fun showFragment(isGranted: Boolean) {
        if (isGranted) {
            router.newRootScreen(Screens.rootAuthScreen)
        } else {
            router.newRootScreen(Screens.rootUnAuthScreen)
        }
    }

    companion object {
        const val PERMISSION_APPROXIMATE_LOCATION: String = Manifest.permission.ACCESS_COARSE_LOCATION
        const val PERMISSION_PRECISE_LOCATION: String = Manifest.permission.ACCESS_FINE_LOCATION
    }
}