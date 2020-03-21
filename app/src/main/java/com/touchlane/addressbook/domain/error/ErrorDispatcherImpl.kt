package com.touchlane.addressbook.domain.error

import android.content.Context
import com.touchlane.addressbook.R
import com.touchlane.addressbook.exception.ContactNotValidException
import com.touchlane.addressbook.exception.ContactWithSuchEmailAlreadyExistsException

class ErrorDispatcherImpl(private val appContext: Context) : ErrorDispatcher {

    override fun dispatch(error: Throwable): String {
        return when(error) {
            is ContactNotValidException -> appContext.getString(R.string.error_cannot_save_not_valid)
            is ContactWithSuchEmailAlreadyExistsException -> appContext.getString(R.string.error_cannot_save_exists)
            else -> appContext.getString(R.string.error_cannot_save_default)
        }
    }
}