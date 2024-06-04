package com.example.demoformapp.presentation.base

import com.example.demoformapp.presentation.form.FormEventType

interface BaseViewModel {
    fun onEvent(type: FormEventType)
}