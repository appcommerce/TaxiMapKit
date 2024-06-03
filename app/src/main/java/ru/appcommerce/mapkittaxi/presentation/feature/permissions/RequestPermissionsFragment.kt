package ru.appcommerce.mapkittaxi.presentation.feature.permissions

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.appcommerce.mapkittaxi.databinding.FragmentPermissionsBinding
import ru.appcommerce.mapkittaxi.presentation.base.BaseFragment
import ru.appcommerce.mapkittaxi.presentation.feature.main.MainViewModel
import ru.appcommerce.mapkittaxi.presentation.states.ToastEvent
import ru.appcommerce.mapkittaxi.presentation.states.UiEvent

class RequestPermissionsFragment : BaseFragment<RequestPermissionsViewModel>() {
    override val viewModel: RequestPermissionsViewModel by viewModel()
    override val binding: FragmentPermissionsBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    private var requestPermissionsResultLauncher: ActivityResultLauncher<Array<String>>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissionsResultLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
            viewModel::onPermissionResult
        )
        binding.btnPermission.setOnClickListener {
            requestPermissionsResultLauncher?.launch(
                arrayOf(
                    MainViewModel.PERMISSION_PRECISE_LOCATION,
                    MainViewModel.PERMISSION_APPROXIMATE_LOCATION
                )
            )
        }
    }

    override fun processEvent(event: UiEvent) {
        val toastEvent = event as? ToastEvent ?: return
        Toast.makeText(requireActivity().applicationContext, toastEvent.message, Toast.LENGTH_LONG).show()
    }
}