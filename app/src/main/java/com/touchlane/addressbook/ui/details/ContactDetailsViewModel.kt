package com.touchlane.addressbook.ui.details

import androidx.databinding.ObservableField
import com.touchlane.addressbook.components.contactRepository
import com.touchlane.addressbook.domain.model.DomainContact
import com.touchlane.addressbook.domain.repository.ContactRepository
import com.touchlane.addressbook.ui.base.BaseAppViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class ContactDetailsViewModel : BaseAppViewModel() {

    val contact: ObservableField<DomainContact?> = ObservableField()
    private val contactsRepository: ContactRepository = contactRepository()

    fun onLoadContact(id: Long) {
        val disposable = contactsRepository.findById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(contact::set)

        addDisposable(disposable)
    }
}
