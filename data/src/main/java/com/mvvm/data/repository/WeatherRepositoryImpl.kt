package com.mvvm.data.repository


import com.mvvm.data.database.dao.WeatherDao
import com.mvvm.data.database.model.WeatherEntity
import com.mvvm.data.networking.WeatherApi
import com.mvvm.data.networking.base.getData
import com.mvvm.domain.entities.WeatherInfo
import com.mvvm.domain.repository.WeatherRepository
import com.mvvm.domain.base.Result
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val weatherApi: WeatherApi,
                            private val weatherDao: WeatherDao
) : BaseRepository<WeatherInfo, WeatherEntity>(), WeatherRepository {
  override suspend fun getWeatherForLocation(location: String): Result<WeatherInfo> {
    return fetchData(
      apiDataProvider = {
        weatherApi.getWeatherForLocation(location).getData(
          fetchFromCacheAction = { weatherDao.getWeatherInfoForCity(location) },
          cacheAction = { weatherDao.saveWeatherInfo(it) })
      },
      dbDataProvider = { weatherDao.getWeatherInfoForCity(location) }
    )
  }
}