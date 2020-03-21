package com.touchlane.addressbook.ui.details

import androidx.databinding.ObservableField
import com.touchlane.addressbook.domain.model.DomainContact
import com.touchlane.addressbook.domain.repository.ContactRepository
import com.touchlane.addressbook.ui.base.BaseAppViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ContactDetailsViewModel(internal val contactRepository: ContactRepository) : BaseAppViewModel() {

    val contact: ObservableField<DomainContact?> = ObservableField()

    fun onLoadContact(id: Long) {
        val disposable = contactRepository.findById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(contact::set) {
                println(it.toString())
            }

        addDisposable(disposable)
    }
}
