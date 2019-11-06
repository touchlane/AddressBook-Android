package com.touchlane.addressbook.ui.edit

import androidx.lifecycle.MutableLiveData
import com.touchlane.addressbook.R
import com.touchlane.addressbook.components.appContext
import com.touchlane.addressbook.components.contactRepository
import com.touchlane.addressbook.domain.repository.ContactRepository
import com.touchlane.addressbook.exception.ContactNotValidException
import com.touchlane.addressbook.exception.ContactWithSuchEmailAlreadyExistsException
import com.touchlane.addressbook.ui.base.BaseAppViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CreateContactViewModel : BaseAppViewModel() {

    val contactInfo = ContactInfo()

    private val contactsRepository: ContactRepository = contactRepository()
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
                val message = dispatchError(it)
                if (it is ContactWithSuchEmailAlreadyExistsException) {
                    contactInfo.emailError.set(message)
                } else {
                    saveResult.value = OperationResult(false, message)
                }
            })
        )
    }

    private fun dispatchError(error: Throwable): String {
        return when(error) {
            is ContactNotValidException -> appContext().getString(R.string.error_cannot_save_not_valid)
            is ContactWithSuchEmailAlreadyExistsException -> appContext().getString(R.string.error_cannot_save_exists)
            else -> appContext().getString(R.string.error_cannot_save_default)
        }
    }

    class OperationResult(
        val success: Boolean,
        val message: String? = null
    )
}