package com.mvvm.data.networking.base

import com.mvvm.data.networking.GENERAL_NETWORK_ERROR
import com.mvvm.domain.base.ResultData
import com.mvvm.domain.base.Failure
import com.mvvm.domain.base.HttpError
import com.mvvm.domain.base.Success
import retrofit2.Response
import java.io.IOException

interface DomainMapper<T : Any> {
  fun mapToDomainModel(): T
}

interface RoomMapper<out T : Any> {
  fun mapToRoomEntity(): T
}

inline fun <T : Any> Response<T>.onSuccess(action: (T) -> Unit): Response<T> {
  if (isSuccessful) body()?.run(action)
  return this
}

inline fun <T : Any> Response<T>.onFailure(action: (HttpError) -> Unit) {
  if (!isSuccessful) errorBody()?.run { action(HttpError(Throwable(message()), code())) }
}

/**
 * Use this if you need to cache data after fetching it from the api, or retrieve something from cache
 */

inline fun <T : RoomMapper<R>, R : DomainMapper<U>, U : Any> Response<T>.getData(
    cacheAction: (R) -> Unit,
    fetchFromCacheAction: () -> R): ResultData<U> {
  try {
    onSuccess {
      val databaseEntity = it.mapToRoomEntity()
      cacheAction(databaseEntity)
      return Success(databaseEntity.mapToDomainModel())
    }
    onFailure {
      val cachedModel = fetchFromCacheAction()
      Success(cachedModel.mapToDomainModel())
    }
    return Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))
  } catch (e: IOException) {
    return Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))
  }
}

/**
 * Use this when communicating only with the api service
 */
fun <T : DomainMapper<R>, R : Any> Response<T>.getData(): ResultData<R> {
  try {
    onSuccess { return Success(it.mapToDomainModel()) }
    onFailure { return Failure(it) }
    return Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))
  } catch (e: IOException) {
    return Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))
  }
}
