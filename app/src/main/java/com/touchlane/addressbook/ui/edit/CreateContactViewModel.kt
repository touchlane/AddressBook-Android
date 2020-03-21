package com.touchlane.addressbook.ui.edit

import androidx.lifecycle.MutableLiveData
import com.touchlane.addressbook.domain.error.ErrorDispatcher
import com.touchlane.addressbook.domain.repository.ContactRepository
import com.touchlane.addressbook.exception.ContactWithSuchEmailAlreadyExistsException
import com.touchlane.addressbook.ui.base.BaseAppViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CreateContactViewModel(private val contactsRepository: ContactRepository,
                             private val errorDispatcher: ErrorDispatcher) : BaseAppViewModel() {

    val contactInfo = ContactInfo()
    val saveResult: MutableLiveData<OperationResult> = MutableLiveData()

    fun doSave() {
        if (contactInfo.validate().not()) {
            return
        }
        val contact = contactInfo.buildContact()

        addDisposable(contactsRepository.save(contact)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                saveResult.value = OperationResult(true)
            }, {
                val message = errorDispatcher.dispatch(it)
                if (it is ContactWithSuchEmailAlreadyExistsException) {
                    contactInfo.emailError.set(message)
                } else {
                    saveResult.value = OperationResult(false, message)
                }
            })
        )
    }

    class OperationResult(
        val success: Boolean,
        val message: String? = null
    )
}