package com.mvvm.data.common.utils

import android.content.Context
import android.net.ConnectivityManager
import com.mvvm.data.common.utils.Connectivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectivityImpl @Inject constructor(@ApplicationContext private val context: Context) : Connectivity {
  
  override fun hasNetworkAccess(): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val info = connectivityManager.activeNetworkInfo
    return info != null && info.isConnected
  }
}