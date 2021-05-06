package com.decagon.android.sq007

object Utils {
    // This is a regex to match Nigerian phone numbers only
    var REGEX = Regex("^(\\+234|0)(8([01])|([79])([0]))\\d{8}$")

    // Validator
    fun phoneNumberValidator(phone: String) = phone.matches(REGEX)
}
