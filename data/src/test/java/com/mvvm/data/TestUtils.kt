package com.mvvm.data

import com.mvvm.data.database.model.WeatherEntity
import com.mvvm.data.networking.model.MainInfo
import com.mvvm.data.networking.model.WeatherInfoResponse
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody

const val CITY_NAME = "Delhi"
const val FAKE_FAILURE_ERROR_CODE = 400
const val UPDATED_CITY_NAME = "London"

val successWeatherInfoResponse = WeatherInfoResponse(0, arrayListOf(), MainInfo(), CITY_NAME)
val failureResponseBody = ResponseBody.create("text".toMediaTypeOrNull(), "network error")
val fakeWeatherEntity = WeatherEntity(0, arrayListOf(), MainInfo(), UPDATED_CITY_NAME)
