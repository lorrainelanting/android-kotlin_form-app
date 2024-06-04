package com.example.demoformapp.presentation.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.demoformapp.R

@Composable
fun ResponseDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogMessage: String
) {
    AlertDialog(
        title = {
            Text(text = stringResource(id = R.string.api_response))
        },
        text = {
            Text(text = dialogMessage)
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text(text = stringResource(id = R.string.okay))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = stringResource(id = R.string.dismiss))
            }
        },
    )
}