package com.mvvm.clean_architecture_showcase_app.ui.weather.view.fragments


import com.mvvm.clean_architecture_showcase_app.R
import com.mvvm.clean_architecture_showcase_app.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherDetailsFragment : BaseFragment() {
  
  override fun getLayout() = R.layout.fragment_weather_details
  
  override fun viewReady() {}
  
  companion object {
    fun newInstance() = WeatherDetailsFragment()
  }
}