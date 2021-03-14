package com.mvvm.domain

import com.mvvm.domain.repository.WeatherRepository
import com.mvvm.domain.usecase.GetWeatherUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherUseCaseTest {
  
  private val weatherRepository: WeatherRepository = mock()
  private val getWeather by lazy { GetWeatherUseCase(weatherRepository) }
  
  @Test
  fun `test GetWeatherUseCase calls WeatherRepository`() {
    runBlocking {
      getWeather(CITY_NAME)
      verify(weatherRepository).getWeatherForLocation(CITY_NAME)
    }
  }

  @Test
  fun `test GetWeatherUseCase calls WeatherRepository with null value`() {
    runBlocking {
      assertEquals(weatherRepository.getWeatherForLocation(null), null)
    }
  }

}