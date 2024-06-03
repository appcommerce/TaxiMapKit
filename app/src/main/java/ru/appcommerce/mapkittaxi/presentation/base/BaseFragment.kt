package ru.appcommerce.mapkittaxi.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ru.appcommerce.mapkittaxi.presentation.states.UiEvent
import ru.appcommerce.mapkittaxi.presentation.states.UiState

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    abstract val viewModel: VM
    abstract val binding: ViewBinding

    private var searchListener: SearchTextHandler? = null

    open fun processState(state: UiState) = Unit
    open fun processEvent(event: UiEvent) = Unit

    open fun searchAction(searchText: String) = Unit
    open fun searchActionIfEmpty() = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.start()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.uiState.observe(viewLifecycleOwner, ::processState)
        viewModel.uiEvent.observe(viewLifecycleOwner, ::processEvent)
    }

    fun AppCompatAutoCompleteTextView.startSearchEvent() {
        searchListener = getSearchListener(this)
        this.addTextChangedListener(searchListener)
    }

    private fun getSearchListener(
        view: AppCompatAutoCompleteTextView
    ): SearchTextHandler = SearchTextHandler(
        view,
        action = ::searchAction,
        actionIfEmpty = ::searchActionIfEmpty
    )
}