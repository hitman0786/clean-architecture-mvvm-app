package com.mvvm.data.repository


import com.mvvm.data.database.dao.WeatherDao
import com.mvvm.data.database.model.WeatherEntity
import com.mvvm.data.networking.DEFAULT_CITY_NAME
import com.mvvm.data.networking.WeatherApi
import com.mvvm.data.networking.base.getData
import com.mvvm.domain.entities.WeatherInfo
import com.mvvm.domain.repository.WeatherRepository
import com.mvvm.domain.base.ResultData
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val weatherApi: WeatherApi,
                            private val weatherDao: WeatherDao
) : BaseRepository<WeatherInfo, WeatherEntity>(), WeatherRepository {
  override suspend fun getWeatherForLocation(location: String?): ResultData<WeatherInfo> {
    return fetchData(
      apiDataProvider = {
        weatherApi.getWeatherForLocation(location ?: DEFAULT_CITY_NAME).getData(
          fetchFromCacheAction = { weatherDao.getWeatherInfoForCity(location ?: DEFAULT_CITY_NAME) },
          cacheAction = { weatherDao.saveWeatherInfo(it) })
      },
      dbDataProvider = { weatherDao.getWeatherInfoForCity(location ?: DEFAULT_CITY_NAME) }
    )
  }
}