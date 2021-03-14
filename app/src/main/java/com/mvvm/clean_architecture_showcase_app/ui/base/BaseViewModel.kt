package com.mvvm.clean_architecture_showcase_app.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvvm.clean_architecture_showcase_app.common.extensions.launch
import com.mvvm.data.common.coroutine.CoroutineContextProvider
import com.mvvm.data.common.utils.Connectivity
import javax.inject.Inject

abstract class BaseViewModel<T : Any, E> : ViewModel() {

  @Inject
  lateinit var coroutineContext: CoroutineContextProvider
  @Inject
  lateinit var connectivity: Connectivity
  
  private val _viewState = MutableLiveData<ViewState<T>>()
  val viewState: LiveData<ViewState<T>>
    get() = _viewState
  
  private val _viewEffects = MutableLiveData<E>()
  val viewEffects: LiveData<E>
    get() = _viewEffects

  //For Main Thread
  fun setViewState(v: ViewState<T>) {
    _viewState.value = v
  }

  //For Background Thread
  fun setViewStateOnWorkerThread(v: ViewState<T>) {
    _viewState.postValue(v)
  }

  protected fun executeUseCase(action: suspend () -> Unit, noInternetAction: () -> Unit) {
    _viewState.value = Loading()
    if (connectivity.hasNetworkAccess()) {
      launch { action() }
    } else {
      noInternetAction()
    }
  }
  
  fun executeUseCase(action: suspend () -> Unit) {
    _viewState.value = Loading()
    launch { action() }
  }
}