package com.mvvm.domain.usecase

import com.mvvm.domain.base.BaseUseCase
import com.mvvm.domain.base.ResultData
import com.mvvm.domain.entities.WeatherInfo
import com.mvvm.domain.repository.WeatherRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
open class GetWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) :
    BaseUseCase<String, WeatherInfo> {

    override suspend operator fun invoke(param: String): ResultData<WeatherInfo> =
        weatherRepository.getWeatherForLocation(param)
}