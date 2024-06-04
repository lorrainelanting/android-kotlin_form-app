package com.example.demoformapp.presentation.form

/** UI State **/
sealed class FormState {
    data object Start : FormState()
    data object Loading : FormState()
    data object FinishLoading : FormState()
    data class Error(val message: String) : FormState()
//    data object DisplayDatePicker : FormState()
//    data class DisplayResultDialog(val response: String) : FormState()
//    data object DisplayGenderSpinner : FormState()
}