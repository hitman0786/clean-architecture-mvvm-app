package com.mvvm.clean_architecture_showcase_app.ui.weather.view.fragments

import androidx.fragment.app.viewModels
import com.mvvm.clean_architecture_showcase_app.ui.weather.presentation.WeatherViewModel
import com.mvvm.clean_architecture_showcase_app.R
import com.mvvm.clean_architecture_showcase_app.common.convertKelvinToCelsius
import com.mvvm.clean_architecture_showcase_app.common.extensions.*
import com.mvvm.clean_architecture_showcase_app.ui.base.*
import com.mvvm.domain.entities.WeatherInfo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_weather.*

@AndroidEntryPoint
class WeatherFragment : BaseFragment() {
  
  private val viewModel: WeatherViewModel by viewModels()
  
  override fun getLayout() = R.layout.fragment_weather
  
  override fun viewReady() {

    subscribeToData()
    
    getWeather.onClick {
      weatherFragmentContainer.hideKeyboard()
      viewModel.getWeatherForLocation(cityInput.text.toString())
    }
    
    showWeatherDetails.onClick {
      activity?.showFragment(WeatherDetailsFragment.newInstance(), R.id.fragmentContainer, true)
    }
  }
  
  private fun subscribeToData() {
    viewModel.viewState.subscribe(this, ::handleViewState)
  }
  
  private fun handleViewState(viewState: ViewState<WeatherInfo>) {
    when (viewState) {
      is Loading -> showLoading(weatherLoadingProgress)
      is Success -> showWeatherData(viewState.data)
      is Error -> handleError(viewState.error.localizedMessage)
      is NoInternetState -> showNoInternetError()
    }
  }
  
  private fun handleError(error: String) {
    hideLoading(weatherLoadingProgress)
    showError(error, weatherFragmentContainer)
  }
  
  private fun showNoInternetError() {
    hideLoading(weatherLoadingProgress)
    snackbar(getString(R.string.no_internet_error_message), weatherFragmentContainer)
  }
  
  private fun showWeatherData(weatherInfo: WeatherInfo) {
    hideLoading(weatherLoadingProgress)
    temperature.text = convertKelvinToCelsius(weatherInfo.temperature)
    pressure.text = weatherInfo.pressure.toString()
    humidity.text = weatherInfo.humidity.toString()
  }
  
  companion object {
    fun newInstance() = WeatherFragment()
  }
}

