package com.mvvm.domain.base

interface BaseUseCase<T : Any, R: Any> {
  suspend operator fun invoke(param: T): ResultData<R>
}