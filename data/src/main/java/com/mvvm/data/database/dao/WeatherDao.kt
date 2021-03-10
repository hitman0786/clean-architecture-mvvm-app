package com.mvvm.data.database.dao

import androidx.room.*
import com.mvvm.data.database.WEATHER_TABLE_NAME
import com.mvvm.data.database.model.WeatherEntity

@Dao
interface WeatherDao {
  
  @Transaction
  suspend fun updateWeatherAndReturn(weather: WeatherEntity): WeatherEntity {
    saveWeatherInfo(weather)
    return getWeatherInfoForCity(weather.name ?: "")
  }
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveWeatherInfo(weather: WeatherEntity)
  
  @Query("SELECT * FROM $WEATHER_TABLE_NAME WHERE name = :city LIMIT 1")
  suspend fun getWeatherInfoForCity(city: String): WeatherEntity
}