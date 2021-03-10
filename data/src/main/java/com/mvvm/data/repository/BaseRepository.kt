package com.mvvm.data.repository

import com.mvvm.data.common.coroutine.CoroutineContextProvider
import com.mvvm.data.common.utils.Connectivity
import com.mvvm.data.networking.GENERAL_NETWORK_ERROR
import com.mvvm.data.networking.base.DomainMapper
import com.mvvm.domain.base.Success
import com.mvvm.domain.base.Failure
import com.mvvm.domain.base.HttpError
import com.mvvm.domain.base.Result
import kotlinx.coroutines.withContext
import javax.inject.Inject

abstract class BaseRepository<T : Any, R : DomainMapper<T>> {
  @Inject
  lateinit var connectivity: Connectivity
  @Inject
  lateinit var contextProvider: CoroutineContextProvider
  
  /**
   * Use this if you need to cache data after fetching it from the api, or retrieve something from cache
   */
  protected suspend fun fetchData(
      apiDataProvider: suspend () -> Result<T>,
      dbDataProvider: suspend () -> R
  ): Result<T> {
    return if (connectivity.hasNetworkAccess()) {
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
  protected suspend fun fetchData(dataProvider: () -> Result<T>): Result<T> {
    return if (connectivity.hasNetworkAccess()) {
      withContext(contextProvider.io) {
        dataProvider()
      }
    } else {
      Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))
    }
  }
}