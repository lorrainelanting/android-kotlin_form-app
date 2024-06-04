package com.example.demoformapp.presentation.form

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.demoformapp.R
import com.example.demoformapp.presentation.dialog.DatePickerDialog
import com.example.demoformapp.presentation.dialog.GenderSpinner
import com.example.demoformapp.presentation.dialog.ResponseDialog

@Composable
fun FormScreen(modifier: Modifier = Modifier, formViewModel: FormViewModel = hiltViewModel()) {
    val uiState by formViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        formViewModel.onEvent(FormEventType.Start)
    }

    when (uiState) {
        is FormState.Error -> {
            Text(text = (uiState as FormState.Error).message)
        }

        FormState.FinishLoading -> {
            CreatedUi(modifier = modifier, formViewModel = formViewModel)

            if (formViewModel.isDialogOpen.value) {
                DatePickerDialog(
                    onDateSelected = {
                        formViewModel.onEvent(FormEventType.SelectDate(it))
                    },
                    onDismiss = {
                        formViewModel.onEvent(FormEventType.DismissDatePicker)
                    }
                )
            }

            if (formViewModel.isGenderSpinnerOpen.value) {
                GenderSpinner(onGenderSelected = {
                    formViewModel.onEvent(FormEventType.GenderChanged(it))
                }, onDismiss = {
                    formViewModel.onEvent(FormEventType.DismissGenderSpinner)
                })
            }

            if (formViewModel.isResponseDialogOpen.value) {
                ResponseDialog(
                    onDismissRequest = {
                        formViewModel.onEvent(FormEventType.DismissResponseDialog)
                    },
                    onConfirmation = {
                        formViewModel.onEvent(FormEventType.ConfirmResponseDialog)
                    },
                    dialogMessage = formViewModel.responseState.value
                )
            }
        }
        FormState.Loading -> {
            Text(text = "Loading...", modifier = modifier)
        }

        FormState.Start -> {
            Text(text = "Start", modifier = modifier)
        }
    }
}

@Composable
private fun CreatedUi(modifier: Modifier, formViewModel: FormViewModel) {
    Column(modifier = modifier, verticalArrangement = Arrangement.SpaceBetween) {
        TextField(
            value = formViewModel.fullNameState.value,
            onValueChange = { formViewModel.onEvent(FormEventType.FullNameChanged(it)) },
            placeholder = { Text(text = stringResource(id = R.string.full_name)) },
            modifier = modifier.fillMaxWidth()
        )

        TextField(
            value = formViewModel.emailAddressState.value,
            onValueChange = { formViewModel.onEvent(FormEventType.EmailAddressChanged(it)) },
            placeholder = { Text(text = stringResource(id = R.string.email_address)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = modifier.fillMaxWidth()
        )

        TextField(
            value = formViewModel.mobileNumberState.value,
            onValueChange = { value ->
                if (value.length <= 11) {
                    val digit = value.filter {
                        it.isDigit()
                    }
                    formViewModel.onEvent(FormEventType.MobileNumberChanged(digit))
                }
            },
            placeholder = { Text(text = stringResource(id = R.string.mobile_number)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = modifier.fillMaxWidth()
        )

        TextField(
            value = formViewModel.birthDateState.value,
            onValueChange = { formViewModel.onEvent(FormEventType.DateOfBirthChanged(it)) },
            placeholder = { Text(text = stringResource(id = R.string.date_of_birth)) },
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    formViewModel.onEvent(FormEventType.ShowDatePickerDialog)
                },
            readOnly = true,
            enabled = false
        )

        TextField(
            value = formViewModel.ageState.value.toString(),
            onValueChange = {
                formViewModel.onEvent(FormEventType.AgeChanged(it.toInt()))
            },
            placeholder = { Text(text = stringResource(id = R.string.age)) },
            modifier = modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.age))},
            readOnly = true,
            enabled = false
        )

        TextField(
            value = formViewModel.genderState.value,
            onValueChange = {
                formViewModel.onEvent(FormEventType.GenderChanged(it))
            },
            placeholder = { Text(text = stringResource(id = R.string.gender)) },
            readOnly = true,
            enabled = false,
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    formViewModel.onEvent(FormEventType.ShowGenderSpinner)
                }
        )

        Button(
            modifier = modifier.fillMaxWidth(),
            onClick = {
                formViewModel.onEvent(FormEventType.Submit)
            }) {
            Text(text = stringResource(id = R.string.submit))
        }
    }
}