package com.mvvm.data.common.coroutine

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

open class CoroutineContextProvider @Inject constructor(){
  open val main: CoroutineContext by lazy { Dispatchers.Main }
  open val io: CoroutineContext by lazy { Dispatchers.IO }
  open val default: CoroutineContext by lazy { Dispatchers.Default }
}

class TestCoroutineContextProvider : CoroutineContextProvider() {
  override val main: CoroutineContext = Dispatchers.Unconfined
  override val io: CoroutineContext = Dispatchers.Unconfined
  override val default: CoroutineContext = Dispatchers.Unconfined
}