package com.example.demoformapp.core.di

import com.example.demoformapp.data.datasource.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
   fun provideApi(): UserApi {
       return UserApi.getInstance()
   }
}