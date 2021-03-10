package com.mvvm.clean_architecture_showcase_app.ui.weather.view.activities

import android.os.Bundle
import com.mvvm.clean_architecture_showcase_app.ui.base.BaseActivity
import com.mvvm.clean_architecture_showcase_app.ui.weather.view.fragments.WeatherFragment
import com.mvvm.clean_architecture_showcase_app.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherActivity : BaseActivity() {
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_container)

      addFragment(WeatherFragment.newInstance(), R.id.fragmentContainer, true)

  }
}
