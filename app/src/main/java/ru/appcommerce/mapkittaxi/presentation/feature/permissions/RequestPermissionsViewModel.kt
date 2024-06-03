package ru.appcommerce.mapkittaxi.presentation.feature.permissions

import com.github.terrakok.cicerone.Router
import ru.appcommerce.mapkittaxi.navigation.Screens
import ru.appcommerce.mapkittaxi.presentation.base.BaseViewModel
import ru.appcommerce.mapkittaxi.presentation.states.ToastEvent

class RequestPermissionsViewModel(
    private val router: Router
) : BaseViewModel() {

    fun onPermissionResult(result: Map<String, Boolean>) {
        val allPermissionsGranted = !result.values.any { granted -> !granted }
        if (allPermissionsGranted) {
            router.newRootScreen(Screens.rootAuthScreen)
        } else {
            pushEvent(
                ToastEvent("Сорян, пустить без разрешения не могу")
            )
        }
    }

}