package com.mvvm.clean_architecture_showcase_app.ui.welcome.view

import android.content.Intent
import android.os.Bundle
import com.mvvm.clean_architecture_showcase_app.ui.weather.view.activities.WeatherActivity
import com.mvvm.clean_architecture_showcase_app.R
import com.mvvm.clean_architecture_showcase_app.common.extensions.onClick
import com.mvvm.clean_architecture_showcase_app.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_welcome.*

@AndroidEntryPoint
class WelcomeActivity : BaseActivity() {
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_welcome)
    
    nextScreen.onClick {
      val intent = Intent(this, WeatherActivity::class.java)
      startActivity(intent)
      finish()
    }
  }
}