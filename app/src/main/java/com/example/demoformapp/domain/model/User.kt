package com.example.demoformapp.domain.model

data class User(
    val id: Long = -1L,
    val fullName: String,
    val emailAddress: String,
    val mobileNumber: String,
    val dateOfBirth: String,
    val age: Int,
    val gender: Int
)
