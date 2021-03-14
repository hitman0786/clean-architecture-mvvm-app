package com.mvvm.domain.repository

import com.mvvm.domain.entities.WeatherInfo
import com.mvvm.domain.base.ResultData

interface WeatherRepository {
  suspend fun getWeatherForLocation(location: String?): ResultData<WeatherInfo>
}