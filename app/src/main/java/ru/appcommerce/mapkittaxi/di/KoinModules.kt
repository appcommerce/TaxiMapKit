package ru.appcommerce.mapkittaxi.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Cicerone.Companion.create
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.appcommerce.mapkittaxi.data.datasource.RouteRemoteDatasource
import ru.appcommerce.mapkittaxi.data.datasource.SuggestionsRemoteDatasource
import ru.appcommerce.mapkittaxi.presentation.feature.main.MainViewModel
import ru.appcommerce.mapkittaxi.presentation.feature.map.MapViewModel
import ru.appcommerce.mapkittaxi.presentation.feature.permissions.RequestPermissionsViewModel
import ru.appcommerce.mapkittaxi.presentation.feature.root.auth.AuthRootViewModel
import ru.appcommerce.mapkittaxi.presentation.feature.root.unauth.UnAuthRootViewModel

val globalNavigationModule = module {
    single(named(RouterType.GLOBAL)) { create() }
    single(named(RouterType.GLOBAL)) { get<Cicerone<Router>>(named(RouterType.GLOBAL)).router }
    single(named(RouterType.GLOBAL)) { get<Cicerone<Router>>(named(RouterType.GLOBAL)).getNavigatorHolder() }
    factory(named(RouterType.GLOBAL)) {
        AppNavigator(
            activity = it.get(),
            containerId = it.get(),
            fragmentManager = it.get(),
            fragmentFactory = it.get()
        )
    }
}

val rootNavigationModule = module {
    single(named(RouterType.ROOT)) { create() }
    single(named(RouterType.ROOT)) { get<Cicerone<Router>>(named(RouterType.ROOT)).router }
    single(named(RouterType.ROOT)) { get<Cicerone<Router>>(named(RouterType.ROOT)).getNavigatorHolder() }
    factory(named(RouterType.ROOT)) {
        AppNavigator(
            activity = it.get(),
            containerId = it.get(),
            fragmentManager = it.get(),
            fragmentFactory = it.get()
        )
    }
}

val mainFeatureModule = module {
    viewModel { MainViewModel(get(named(RouterType.GLOBAL))) }
    viewModel { AuthRootViewModel(get(named(RouterType.ROOT))) }
    viewModel { UnAuthRootViewModel(get(named(RouterType.ROOT)))  }
    viewModel { MapViewModel(get(), get()) }
    viewModel { RequestPermissionsViewModel(get(named(RouterType.GLOBAL))) }
}

val dataSourceModule = module {
    single { RouteRemoteDatasource() }
    single { SuggestionsRemoteDatasource() }
}

val koinModules = listOf(
    globalNavigationModule,
    rootNavigationModule,
    mainFeatureModule,
    dataSourceModule
)


enum class RouterType {
    GLOBAL,
    ROOT
}