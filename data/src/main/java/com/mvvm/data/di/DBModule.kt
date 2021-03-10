package com.mvvm.data.di

import android.content.Context
import androidx.room.Room
import com.mvvm.data.database.WEATHER_TABLE_NAME
import com.mvvm.data.database.WeatherDatabase
import com.mvvm.data.database.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DBModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): WeatherDatabase {
        return Room.databaseBuilder(
            appContext,
            WeatherDatabase::class.java,
            WEATHER_TABLE_NAME
        ).build()
    }

    @Provides
    fun provideLogDao(database: WeatherDatabase): WeatherDao {
        return database.weatherDao()
    }
}