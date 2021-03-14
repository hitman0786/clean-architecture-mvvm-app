package com.mvvm.clean_architecture_showcase_app.ui.weather.presentation


import com.mvvm.clean_architecture_showcase_app.ui.base.BaseViewModel
import com.mvvm.clean_architecture_showcase_app.ui.base.Error
import com.mvvm.clean_architecture_showcase_app.ui.base.Success
import com.mvvm.clean_architecture_showcase_app.ui.weather.view.WeatherViewEffects
import com.mvvm.clean_architecture_showcase_app.common.DEFAULT_CITY_NAME
import com.mvvm.domain.base.onFailure
import com.mvvm.domain.base.onSuccess
import com.mvvm.domain.entities.WeatherInfo
import com.mvvm.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val getWeather: GetWeatherUseCase) : BaseViewModel<WeatherInfo, WeatherViewEffects>() {
  
  fun getWeatherForLocation(location: String = DEFAULT_CITY_NAME) = executeUseCase {
        getWeather(location)
            .onSuccess {
                setViewState(Success(it))
            }
            .onFailure {
                setViewState(Error(it.throwable))
            }
      }
}
