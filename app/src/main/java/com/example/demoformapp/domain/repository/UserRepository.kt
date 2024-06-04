package com.example.demoformapp.domain.repository

import com.example.demoformapp.domain.model.User

interface UserRepository {
//    suspend fun getUser(id: Int): User

    /// returns token
    suspend fun register(user: User): String
}