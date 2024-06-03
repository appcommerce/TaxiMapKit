package ru.appcommerce.mapkittaxi.presentation.base

import android.text.Editable
import android.text.TextWatcher

abstract class BaseSearchTextHandler : TextWatcher {

    abstract val validate: (searchText: String) -> Unit

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    override fun afterTextChanged(s: Editable?) = validate(s.toString())

}
