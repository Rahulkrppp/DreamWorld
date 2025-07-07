package com.app.dreamworld.data.remote.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.app.dreamworld.data.remote.di.helper.AuthenticationRepoHelper
import com.app.dreamworld.ui.auth.AuthenticationRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideAuthenticationRepo(authenticationRepository: AuthenticationRepository): AuthenticationRepoHelper {
        return authenticationRepository
    }


}