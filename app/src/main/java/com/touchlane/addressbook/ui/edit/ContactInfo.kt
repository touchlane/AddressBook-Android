package com.touchlane.addressbook.ui.edit

import androidx.databinding.ObservableField
import com.touchlane.addressbook.domain.model.DomainContact
import com.touchlane.addressbook.ui.edit.binding.ErrorMessageBindingAdapter
import com.touchlane.addressbook.ui.edit.binding.ValidationRule

data class ContactInfo(
    val firstName: ObservableField<String> = ObservableField(""),
    val firstNameError: ObservableField<String> = ObservableField(""),

    val lastName: ObservableField<String> = ObservableField(""),
    val lastNameError: ObservableField<String> = ObservableField(""),

    val email: ObservableField<String> = ObservableField(""),
    val emailError: ObservableField<String> = ObservableField(""),

    val phone: ObservableField<String> = ObservableField(""),
    val phoneError: ObservableField<String> = ObservableField(""),

    val address: ObservableField<String> = ObservableField("")
) {

    fun validate(): Boolean {
        return validateField(firstName, firstNameError, ErrorMessageBindingAdapter.firstNameRule) and
                validateField(lastName, lastNameError, ErrorMessageBindingAdapter.lastNameRule) and
                validateField(email, emailError, ErrorMessageBindingAdapter.emailRule) and
                validateField(phone, phoneError, ErrorMessageBindingAdapter.phoneRule)
    }

    private fun validateField(data: ObservableField<String>,
                              error: ObservableField<String>,
                              rule: ValidationRule): Boolean {
        val (valid, message) = rule.validate(data.get() ?: "")
        error.set(message)
        return valid
    }

    fun buildContact(): DomainContact {
        return DomainContact(
            firstName = firstName.get()!!,
            lastName = lastName.get()!!,
            email = email.get()!!,
            address = address.get()!!,
            cellPhone = phone.get()!!
        )
    }
}