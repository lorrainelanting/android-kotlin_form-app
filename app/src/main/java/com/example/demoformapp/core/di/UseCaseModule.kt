package com.example.demoformapp.core.di

import com.example.demoformapp.domain.repository.UserRepository
import com.example.demoformapp.domain.usecase.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {
    @Provides
    fun provideRegisterUseCase(repository: UserRepository): RegisterUseCase {
        return RegisterUseCase(repository)
    }
}