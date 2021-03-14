package com.mvvm.domain.base

sealed class ResultData<out T : Any>
data class Success<out T : Any>(val data: T) : ResultData<T>()
data class Failure(val httpError: HttpError) : ResultData<Nothing>()

class HttpError(val throwable: Throwable, val errorCode: Int = 0)

inline fun <T : Any> ResultData<T>.onSuccess(action: (T) -> Unit): ResultData<T> {
  if (this is Success) action(data)
  return this
}

inline fun <T : Any> ResultData<T>.onFailure(action: (HttpError) -> Unit) {
  if (this is Failure) action(httpError)
}