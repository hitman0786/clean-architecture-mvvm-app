package com.mvvm.data.repository

import android.content.Context
import com.mvvm.data.common.coroutine.CoroutineContextProvider
import com.mvvm.data.common.utils.Connectivity
import com.mvvm.data.common.utils.ConnectivityImpl
import com.mvvm.data.networking.GENERAL_NETWORK_ERROR
import com.mvvm.data.networking.base.DomainMapper
import com.mvvm.domain.base.Success
import com.mvvm.domain.base.Failure
import com.mvvm.domain.base.HttpError
import com.mvvm.domain.base.ResultData
import kotlinx.coroutines.withContext
import javax.inject.Inject

abstract class BaseRepository<T : Any, R : DomainMapper<T>> {
  @Inject
  lateinit var connectivity: Connectivity
  @Inject
  lateinit var contextProvider: CoroutineContextProvider

  private fun isInternetConnected() = connectivity.hasNetworkAccess()
  /**
   * Use this if you need to cache data after fetching it from the api, or retrieve something from cache
   */
  protected suspend fun fetchData(
      apiDataProvider: suspend () -> ResultData<T>,
      dbDataProvider: suspend () -> R
  ): ResultData<T> {
    return if (isInternetConnected()) {
      withContext(contextProvider.io) {
        apiDataProvider()
      }
    } else {
      withContext(contextProvider.io) {
        val dbResult = dbDataProvider()
        Success(dbResult.mapToDomainModel())
      }
    }
  }
  
  /**
   * Use this when communicating only with the api service
   */
  protected suspend fun fetchData(dataProvider: () -> ResultData<T>): ResultData<T> {
    return if (isInternetConnected()) {
      withContext(contextProvider.io) {
        dataProvider()
      }
    } else {
      Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))
    }
  }

  //for Testing
  fun initializeConnectivity(context: Context) {
    connectivity = ConnectivityImpl(context)
  }

  fun initializeContextProvider() {
    contextProvider = CoroutineContextProvider()
  }
}