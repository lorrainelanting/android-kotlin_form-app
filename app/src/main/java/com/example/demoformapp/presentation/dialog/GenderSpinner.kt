package com.example.demoformapp.presentation.dialog

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun GenderSpinner(
    onGenderSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val genderOptions = listOf("Male", "Female")

    DropdownMenu(expanded = true, onDismissRequest = { onDismiss() }) {
        genderOptions.forEachIndexed { index, gender ->
            DropdownMenuItem(
                text = { Text(text = gender) },
                onClick = {
                    onGenderSelected(gender)
                })
        }
    }

}