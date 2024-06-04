package com.example.demoformapp.core.di

import com.example.demoformapp.data.repository.UserRepositoryImpl
import com.example.demoformapp.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun providesUserRepository(repository: UserRepositoryImpl): UserRepository
}