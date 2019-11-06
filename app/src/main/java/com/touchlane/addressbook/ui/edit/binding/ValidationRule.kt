package com.touchlane.addressbook.ui.edit.binding

import android.text.Editable

interface ValidationRule {

    fun validate(text: CharSequence): ValidationInfo

    data class ValidationInfo(
        val valid: Boolean,
        val message: String? = null
    )
}