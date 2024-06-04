package com.example.demoformapp.presentation.util

import java.text.SimpleDateFormat
import java.util.Date

class DateFormat {
    fun formatDate(dateString: String): Date? {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        return if (dateString != "") {
            sdf.parse(dateString)
        } else {
            Date()
        }
    }

    fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        return formatter.format(Date(millis))
    }
}