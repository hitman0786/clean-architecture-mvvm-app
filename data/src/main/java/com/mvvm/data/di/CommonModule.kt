package com.mvvm.data.di

import com.mvvm.data.common.utils.Connectivity
import com.mvvm.data.common.utils.ConnectivityImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class CommonModule {
    @Binds
    abstract fun provideStorage(connectivityImpl: ConnectivityImpl): Connectivity
}