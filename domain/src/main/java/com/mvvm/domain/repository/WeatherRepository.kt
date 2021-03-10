package com.mvvm.domain.repository

import com.mvvm.domain.entities.WeatherInfo
import com.mvvm.domain.base.Result

interface WeatherRepository {
  suspend fun getWeatherForLocation(location: String): Result<WeatherInfo>
}