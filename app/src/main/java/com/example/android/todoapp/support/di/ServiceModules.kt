

package com.example.android.todoapp.support.di

import com.example.android.todoapp.service.auth.AppAuthService
import com.example.android.todoapp.service.auth.DefaultAppAuthService
import com.example.android.todoapp.service.auth.source.local.AuthLocalSource
import com.example.android.todoapp.service.auth.source.local.DefaultAuthLocalSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModules {

    @Singleton
    @Binds
    abstract fun bindAppAuthService(repository: DefaultAppAuthService): AppAuthService

    @Binds
    abstract fun bindLocalAuthService(repository: DefaultAuthLocalSource): AuthLocalSource
}
