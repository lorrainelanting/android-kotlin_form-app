package com.example.demoformapp.presentation.form

sealed class FormEventType {
    data object Start : FormEventType()
    data object Success : FormEventType()
    data class Error(val message: String) : FormEventType()
    data object Submit : FormEventType()
    data object ShowDatePickerDialog : FormEventType()
    data class SelectDate(val selectedDate: String) : FormEventType()
    data object DismissDatePicker : FormEventType()
    data object ShowGenderSpinner : FormEventType()
    data object DismissGenderSpinner : FormEventType()
    data object ConfirmResponseDialog: FormEventType()
    data object DismissResponseDialog: FormEventType()

    // form fields
    data class FullNameChanged(val fullName: String) : FormEventType()
    data class EmailAddressChanged(val emailAddress: String) : FormEventType()
    data class MobileNumberChanged(val mobileNumber: String) : FormEventType()
    data class DateOfBirthChanged(val birthDate: String) : FormEventType()
    data class AgeChanged(val age: Int) : FormEventType()
    data class GenderChanged(val gender: String) : FormEventType()
}