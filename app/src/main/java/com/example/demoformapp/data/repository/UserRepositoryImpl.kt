package com.example.demoformapp.data.repository

import com.example.demoformapp.data.datasource.api.UserApi
import com.example.demoformapp.data.datasource.api.model.RegisterRequest
import com.example.demoformapp.domain.model.User
import com.example.demoformapp.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(private val userApi: UserApi) : UserRepository {
    @Throws
    override suspend fun register(user: User): String {
        try {
            val id = 1
            val registerRequest = RegisterRequest(
                id = user.id,
                fullName = user.fullName,
                emailAddress = user.emailAddress,
                mobileNumber = user.mobileNumber,
                dateOfBirth = user.dateOfBirth,
                age = user.age,
                gender = user.gender
            )

            val response = userApi.save(registerRequest)
            val registerResponse = response.body()

            if (registerResponse == null){
                throw Exception("Response is null")
            } else {
                return registerResponse.token
            }
        } catch (e: retrofit2.HttpException) {
            throw e
        }
    }
}