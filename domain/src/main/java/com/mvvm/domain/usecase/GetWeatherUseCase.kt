package com.mvvm.domain.usecase

import com.mvvm.domain.base.BaseUseCase
import com.mvvm.domain.base.Result
import com.mvvm.domain.entities.WeatherInfo
import com.mvvm.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) :
    BaseUseCase<String, WeatherInfo> {

    override suspend operator fun invoke(param: String): Result<WeatherInfo> =
        weatherRepository.getWeatherForLocation(param)
}