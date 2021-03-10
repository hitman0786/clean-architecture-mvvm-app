package com.mvvm.clean_architecture_showcase_app.di

import com.mvvm.data.repository.WeatherRepositoryImpl
import com.mvvm.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository = weatherRepositoryImpl
}
