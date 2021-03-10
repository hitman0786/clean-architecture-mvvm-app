package com.mvvm.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mvvm.data.database.dao.WeatherDao
import com.mvvm.data.database.model.WeatherEntity

@Database(entities = [WeatherEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {
  
  abstract fun weatherDao(): WeatherDao
}