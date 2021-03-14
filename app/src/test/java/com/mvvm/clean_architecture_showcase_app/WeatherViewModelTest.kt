package com.mvvm.clean_architecture_showcase_app


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mvvm.clean_architecture_showcase_app.ui.base.Loading
import com.mvvm.clean_architecture_showcase_app.ui.base.Success
import com.mvvm.clean_architecture_showcase_app.ui.base.ViewState
import com.mvvm.clean_architecture_showcase_app.ui.weather.presentation.WeatherViewModel
import com.mvvm.domain.entities.WeatherInfo
import com.mvvm.domain.usecase.GetWeatherUseCase
import com.nhaarman.mockitokotlin2.mock
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val getWeather: GetWeatherUseCase = mock()
    private val observer: Observer<ViewState<WeatherInfo>> = com.mvvm.clean_architecture_showcase_app.mock()

    @Captor
    private val captor: ArgumentCaptor<ViewState<WeatherInfo>>? = null
    private var weatherViewModel: WeatherViewModel? = null
    @Captor
    private val captor1: ArgumentCaptor<ViewState<WeatherInfo>>? = null

    @Before
    fun setup(){
        weatherViewModel = WeatherViewModel(getWeather)
    }

    @Test
    fun `test getWeather liveData view states`() {
        testCoroutineRule.runBlockingTest {
            weatherViewModel?.viewState?.observeForever(observer)

            getWeather(CITY_NAME)
            //case 1
            weatherViewModel?.getWeatherForLocation(CITY_NAME)

            //case 2
            val weatherInfo = WeatherInfo(200.0, 23, 34.0)
            weatherViewModel?.setViewState(Success(weatherInfo))

            captor?.run {
                verify(observer, times(2)).onChanged(capture())
                val (loading, success) = allValues
                assertEquals(Loading::class.java, loading.javaClass)//loading
                assertEquals(success::class.java, success.javaClass)//success
                weatherViewModel?.viewState?.removeObserver(observer)
            }
         }
    }

    @Test
    fun `test weather values with live data`() {

        testCoroutineRule.runBlockingTest {
            weatherViewModel?.viewState?.observeForever(observer)

            getWeather(CITY_NAME)

            weatherViewModel?.getWeatherForLocation(CITY_NAME)

            val weatherInfo = WeatherInfo(200.0, 23, 34.0)
            weatherViewModel?.setViewState(Success(weatherInfo))

            captor?.run {
                verify(observer, times(2)).onChanged(capture())
                val (loading, success) = allValues
                assertEquals(Loading::class.java, loading.javaClass)//loading
                assertEquals(success::class.java, success.javaClass)//success
                val data: WeatherInfo = (success as Success).data
                assertEquals(23, data.humidity)
                weatherViewModel?.viewState?.removeObserver(observer)
            }
        }

    }
}

//for more info to test live data different api state
//https://stackoverflow.com/questions/48249816/argumentcaptor-vs-inorder-to-verify-subsequent-callbacks-with-different-argument