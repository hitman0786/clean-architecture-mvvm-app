package com.mvvm.data


import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.mvvm.data.database.dao.WeatherDao
import com.mvvm.data.networking.WeatherApi
import com.mvvm.data.repository.WeatherRepositoryImpl
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Response

@Config(manifest=Config.NONE, sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class WeatherRepositoryTest {

    private val weatherTestApi: WeatherApi = mock()
    private val weatherDao: WeatherDao = mock()
    private val weatherRepository = WeatherRepositoryImpl(weatherTestApi, weatherDao)

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        weatherRepository.initializeConnectivity(context)
        weatherRepository.initializeContextProvider()
    }

   @Test
    fun `test api calls and saves data to db if success`() {
        runBlocking {
            whenever(weatherTestApi.getWeatherForLocation(CITY_NAME)).thenReturn(
                Response.success(
                    successWeatherInfoResponse
                )
            )
            whenever(weatherDao.updateWeatherAndReturn(successWeatherInfoResponse.mapToRoomEntity())).thenReturn(
                fakeWeatherEntity
            )

            weatherRepository.getWeatherForLocation(CITY_NAME)
            verify(weatherTestApi, times(1)).getWeatherForLocation(CITY_NAME)

            weatherDao.updateWeatherAndReturn(fakeWeatherEntity)
            weatherDao.getWeatherInfoForCity(UPDATED_CITY_NAME)
            verify(weatherDao).getWeatherInfoForCity(UPDATED_CITY_NAME)
        }
    }

    @Test
    fun `test api calls and returns data from db if failure`() {
        runBlocking {
            `when`(weatherTestApi.getWeatherForLocation(CITY_NAME)).thenReturn(Response.error(FAKE_FAILURE_ERROR_CODE, failureResponseBody))
            weatherTestApi.getWeatherForLocation(CITY_NAME)
            verify(weatherTestApi, times(1)).getWeatherForLocation(CITY_NAME)

            verify(weatherDao, never()).updateWeatherAndReturn(fakeWeatherEntity)

            weatherDao.getWeatherInfoForCity(CITY_NAME)
            verify(weatherDao).getWeatherInfoForCity(CITY_NAME)
        }
    }
}