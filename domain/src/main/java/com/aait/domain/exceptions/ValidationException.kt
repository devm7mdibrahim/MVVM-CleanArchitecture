package com.aait.domain.exceptions

sealed class ValidationException: Exception(){
    object InValidPhoneException : ValidationException()
    object InValidNameException : ValidationException()
    object InValidPriceException : ValidationException()
    object InValidTextException : ValidationException()
    object InValidPasswordException : ValidationException()
    object InValidConfirmationPasswordException : ValidationException()
    object InValidEmailAddressException : ValidationException()
    object InValidCountryIsoException : ValidationException()
    object InValidDeviceIdException : ValidationException()
    object InValidVerificationCodeException : ValidationException()
}
