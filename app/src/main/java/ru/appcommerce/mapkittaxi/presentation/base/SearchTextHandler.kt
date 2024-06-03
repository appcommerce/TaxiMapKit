package ru.appcommerce.mapkittaxi.presentation.base

import androidx.appcompat.widget.AppCompatAutoCompleteTextView

class SearchTextHandler(
    view: AppCompatAutoCompleteTextView,
    action: (argument: String) -> Unit,
    actionIfEmpty: () -> Unit
) : BaseSearchTextHandler() {

    override val validate: (searchText: String) -> Unit = { s ->
        view.removeTextChangedListener(this)
        if (s.isNotEmpty() && !s.startsWith(" ", ignoreCase = true)){
            action(s)
        }else if(s.isEmpty() && !s.startsWith(" ", ignoreCase = true)){
            actionIfEmpty()
        }
        view.addTextChangedListener(this)
    }

}
