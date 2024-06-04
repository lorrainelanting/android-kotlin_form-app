package com.example.demoformapp.domain.usecase

import com.example.demoformapp.domain.model.User
import com.example.demoformapp.domain.repository.UserRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(user: User): String {
        return userRepository.register(user)
    }
}