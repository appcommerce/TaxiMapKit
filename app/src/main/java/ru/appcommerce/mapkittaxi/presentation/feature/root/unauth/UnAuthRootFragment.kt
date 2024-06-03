package ru.appcommerce.mapkittaxi.presentation.feature.root.unauth

import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.qualifier
import ru.appcommerce.mapkittaxi.R
import ru.appcommerce.mapkittaxi.databinding.FragmentRootBinding
import ru.appcommerce.mapkittaxi.di.RouterType
import ru.appcommerce.mapkittaxi.presentation.base.BaseFragment

class UnAuthRootFragment : BaseFragment<UnAuthRootViewModel>() {

    override val viewModel: UnAuthRootViewModel by viewModel()
    override val binding: FragmentRootBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    private val navigator: AppNavigator by inject(qualifier = qualifier(RouterType.ROOT)) {
        parametersOf(
            requireActivity(),
            R.id.root_container,
            childFragmentManager,
            childFragmentManager.fragmentFactory
        )
    }

    private val navigatorHolder: NavigatorHolder by inject(qualifier = qualifier(RouterType.ROOT))

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

}