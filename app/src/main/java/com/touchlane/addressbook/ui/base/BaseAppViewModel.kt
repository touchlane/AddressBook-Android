package com.touchlane.addressbook.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseAppViewModel : ViewModel() {

    private val actionsDisposable = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable) {
        actionsDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        actionsDisposable.dispose()
    }
}