package com.mvvm.clean_architecture_showcase_app

import org.mockito.Mockito

inline fun <reified T> mock(): T = Mockito.mock(T::class.java)