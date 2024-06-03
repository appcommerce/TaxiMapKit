package ru.appcommerce.mapkittaxi.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.appcommerce.mapkittaxi.presentation.feature.map.MapFragment
import ru.appcommerce.mapkittaxi.presentation.feature.permissions.RequestPermissionsFragment
import ru.appcommerce.mapkittaxi.presentation.feature.root.unauth.UnAuthRootFragment
import ru.appcommerce.mapkittaxi.presentation.feature.root.auth.AuthRootFragment

object Screens {

    val mapScreen = FragmentScreen {
        MapFragment()
    }

    val rootAuthScreen = FragmentScreen {
        AuthRootFragment()
    }

    val rootUnAuthScreen = FragmentScreen {
        UnAuthRootFragment()
    }

    val requestPermissionsScreen = FragmentScreen {
        RequestPermissionsFragment()
    }
}