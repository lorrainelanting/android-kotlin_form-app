package com.example.demoformapp.data.datasource.api.model

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("id") val id: Long,
    @SerializedName("fullName") val fullName: String,
    @SerializedName("emailAddress") val emailAddress: String,
    @SerializedName("mobileNumber") val mobileNumber: String,
    @SerializedName("dateOfBirth") val dateOfBirth: String,
    @SerializedName("age") val age: Int,
    @SerializedName("gender") val gender: Int,
//    @SerializedName("id") var createdAtTimeStamp: Long = System.currentTimeMillis() / 1000
)
