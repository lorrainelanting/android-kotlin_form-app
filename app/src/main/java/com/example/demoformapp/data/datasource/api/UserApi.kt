package com.example.demoformapp.data.datasource.api

import com.example.demoformapp.data.datasource.api.model.RegisterRequest
import com.example.demoformapp.data.datasource.api.model.RegisterResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    @POST("${BASE_URL}91af217e-8701-47e7-9a28-89487a01025c")
    suspend fun save(
        @Body registerRequest: RegisterRequest
    ): Response<RegisterResponse>

    companion object {
        private const val BASE_URL = "https://run.mocky.io/v3/"

        fun getInstance(): UserApi {
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()

            return retrofit.create(UserApi::class.java)
        }
    }
}