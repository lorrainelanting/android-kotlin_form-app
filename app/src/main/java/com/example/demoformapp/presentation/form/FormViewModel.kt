package com.example.demoformapp.presentation.form

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoformapp.domain.model.User
import com.example.demoformapp.domain.usecase.RegisterUseCase
import com.example.demoformapp.presentation.base.BaseViewModel
import com.example.demoformapp.presentation.util.DateFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(private val registerUseCase: RegisterUseCase) : ViewModel(),
    BaseViewModel {
    private val _uiState: MutableStateFlow<FormState> = MutableStateFlow(FormState.Start)
    val uiState: StateFlow<FormState> = _uiState

    private val _responseState: MutableState<String> = mutableStateOf("")
    val responseState: MutableState<String> = _responseState

    // dialog states
    private val _isDialogOpen: MutableState<Boolean> = mutableStateOf(false)
    val isDialogOpen: State<Boolean> = _isDialogOpen

    private val _isGenderSpinnerOpen: MutableState<Boolean> = mutableStateOf(false)
    val isGenderSpinnerOpen: State<Boolean> = _isGenderSpinnerOpen

    private val _isResponseDialogOpen: MutableState<Boolean> = mutableStateOf(false)
    val isResponseDialogOpen: State<Boolean> = _isResponseDialogOpen

    //  END -- dialog states

    // form field states
    private val _fullNameState: MutableState<String> = mutableStateOf("")
    val fullNameState: State<String> = _fullNameState

    private val _emailAddressState: MutableState<String> = mutableStateOf("")
    val emailAddressState: State<String> = _emailAddressState

    private val _mobileNumberState: MutableState<String> = mutableStateOf("")
    val mobileNumberState: State<String> = _mobileNumberState

    private val _birthDateState: MutableState<String> = mutableStateOf("")
    val birthDateState: State<String> = _birthDateState

    private val _ageState: MutableState<Int> = mutableIntStateOf(18)
    val ageState: State<Int> = _ageState

    private val _genderState: MutableState<String> = mutableStateOf("")
    val genderState: State<String> = _genderState
    // END -- form field states

    override fun onEvent(type: FormEventType) {
        when (type) {
            is FormEventType.AgeChanged -> TODO()
            is FormEventType.DateOfBirthChanged -> TODO()
            is FormEventType.EmailAddressChanged -> onEmailAddressChanged(type.emailAddress)
            is FormEventType.Error -> onSubmitFailed(type.message)
            is FormEventType.FullNameChanged -> onFullNameChanged(type.fullName)
            is FormEventType.GenderChanged -> onGenderChanged(type.gender)
            is FormEventType.MobileNumberChanged -> onMobileNumberChanged(type.mobileNumber)
            FormEventType.Start -> onStart()
            FormEventType.Submit -> onSubmit()
            FormEventType.Success -> TODO()
            FormEventType.ShowDatePickerDialog -> onDateOfBirthClicked()
            FormEventType.DismissDatePicker -> onDismissDatePicker()
            is FormEventType.SelectDate -> onDateSelected(type.selectedDate)
            FormEventType.ShowGenderSpinner -> onGenderClicked()
            FormEventType.DismissGenderSpinner -> onDismissGenderSpinner()
            FormEventType.DismissResponseDialog -> onDismissResponseDialog()
            is FormEventType.ConfirmResponseDialog -> onConfirmResponseDialog()
        }
    }

    private fun onConfirmResponseDialog() {
        _isResponseDialogOpen.value = false
    }

    private fun onDismissResponseDialog() {
        _isResponseDialogOpen.value = false
    }

    private fun onDismissGenderSpinner() {
        _isGenderSpinnerOpen.value = false
    }

    private fun onGenderClicked() {
        _isGenderSpinnerOpen.value = true
    }

    private fun onMobileNumberChanged(mobileNumber: String) {
        _mobileNumberState.value = mobileNumber
    }

    private fun onGenderChanged(gender: String) {
        _genderState.value = gender
        _isGenderSpinnerOpen.value = false
    }

    private fun onFullNameChanged(fullName: String) {
        _fullNameState.value = fullName
    }

    private fun onEmailAddressChanged(emailAddress: String) {
        _emailAddressState.value = emailAddress
    }

    private fun onStart() {
        onViewCreated()
    }

    private fun onViewCreated() {
        _uiState.value = FormState.Loading

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                delay(1000)
            }

            withContext(Dispatchers.Main) {
                _uiState.value = FormState.FinishLoading
            }
        }
    }

    private fun onSubmit() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val gender = when (genderState.value) {
                        "Male" -> {
                            0
                        }
                        "Female" -> {
                            1
                        }
                        else -> {
                            -1
                        }
                    }

                    val user = User(
                        fullName = _fullNameState.value,
                        emailAddress = _emailAddressState.value,
                        mobileNumber = _mobileNumberState.value,
                        dateOfBirth = _birthDateState.value,
                        age = _ageState.value,
                        gender = gender
                    )
                    val response: String = registerUseCase.invoke(user)

                    withContext(Dispatchers.Main) {
                        onSubmitSuccess(response)
                    }

                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        onSubmitFailed(e.localizedMessage?.toString() ?: "Http connection error.")
                    }
                }
            }
        }
    }

    private fun onSubmitSuccess(response: String) {
        _responseState.value = response
        _isResponseDialogOpen.value = true
    }

    private fun onSubmitFailed(message: String) {
        _uiState.value = FormState.Error(message)
    }

    private fun onDateOfBirthClicked() {
        _isDialogOpen.value = true
    }

    private fun onDateSelected(date: String) {
        _birthDateState.value = date
        _isDialogOpen.value = false
        calcAge()
    }

    private fun onDismissDatePicker() {
        _isDialogOpen.value = false
    }

    private fun calcAge() {
        val date = Date()
        val dob = Calendar.getInstance()
        val today = Calendar.getInstance()

        dob.time = DateFormat().formatDate(birthDateState.value) ?: date
        val year = dob.get(Calendar.YEAR)
        val month = dob.get(Calendar.MONTH)
        val day = dob.get(Calendar.DAY_OF_MONTH)

        dob.set(year, month + 1, day)

        var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)

        if (today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
            age--
        }

        _ageState.value = age
    }
}