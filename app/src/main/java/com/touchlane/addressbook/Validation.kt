package com.touchlane.addressbook

import com.touchlane.addressbook.db.model.DbContact

const val EMAIL_REGEX = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
        "\\@" +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
        "(" +
        "\\." +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
        ")+"

const val PHONE_REGEX = "\\+?[0-9\\- ]+"

fun isValid(contact: DbContact): Boolean {
    return contact.firstName.isNotBlank()
            && contact.lastName.isNotBlank()
            && contact.email.matches(Regex(EMAIL_REGEX))
            && contact.phone.matches(Regex(PHONE_REGEX))
}
