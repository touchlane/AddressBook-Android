package com.touchlane.addressbook.ui.edit.binding

import androidx.annotation.StringRes
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout
import androidx.databinding.BindingAdapter
import com.touchlane.addressbook.EMAIL_REGEX
import com.touchlane.addressbook.PHONE_REGEX
import com.touchlane.addressbook.R
import com.touchlane.addressbook.components.appContext

/**
 * Used as a error message and validation for the form for new contact
 * Validation rules can be easily added for any field, just extend ValidationRule interface
 */
object ErrorMessageBindingAdapter {

    @BindingAdapter("app:validation")
    @JvmStatic
    fun setErrorEnable(
        textInputLayout: TextInputLayout,
        validationRule: ValidationRule
    ) {
        textInputLayout.editText?.addTextChangedListener {
            val validationResult = validationRule.validate(it!!)
            textInputLayout.isErrorEnabled = validationResult.valid.not()
            textInputLayout.error = validationResult.message
        }
    }

    @JvmStatic
    val firstNameRule = NotEmptyRule(R.string.error_fist_name_required)

    @JvmStatic
    val lastNameRule = NotEmptyRule(R.string.error_last_name_required)

    @JvmStatic
    val emailRule = NotEmptyRuleWIthPattern(R.string.error_email_required, R.string.error_email_invalid, EMAIL_REGEX)

    @JvmStatic
    val phoneRule = NotEmptyRuleWIthPattern(R.string.error_phone_required, R.string.error_phone_invalid, PHONE_REGEX)

    class NotEmptyRule(@StringRes private val errorId: Int) : ValidationRule {

        override fun validate(text: CharSequence): ValidationRule.ValidationInfo {
            val valid = text.toString().isNotBlank()
            val message = if (valid) null else appContext().getString(errorId)
            return ValidationRule.ValidationInfo(valid, message)
        }
    }

    class NotEmptyRuleWIthPattern(@StringRes private val emptyErrorId: Int,
                                  @StringRes private val notMatches: Int,
                                  private val regex: String) : ValidationRule {

        override fun validate(text: CharSequence): ValidationRule.ValidationInfo {
            val blank = text.toString().isBlank()
            if (blank) {
                return ValidationRule.ValidationInfo(false, appContext().getString(emptyErrorId))
            }
            val matches = text.matches(Regex(regex))
            if (matches) {
                return ValidationRule.ValidationInfo(true, null)
            } else {
                return ValidationRule.ValidationInfo(false, appContext().getString(notMatches))
            }

        }
    }
}