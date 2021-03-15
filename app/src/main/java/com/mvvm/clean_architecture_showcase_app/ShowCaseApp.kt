package com.mvvm.clean_architecture_showcase_app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShowCaseApp: Application() {
    override fun onCreate() {
        super.onCreate()
        //Here initialise project level things
    }
}