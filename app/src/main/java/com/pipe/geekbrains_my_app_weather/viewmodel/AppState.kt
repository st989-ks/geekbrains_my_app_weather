package com.pipe.geekbrains_my_app_weather.viewmodel

import com.pipe.geekbrains_my_app_weather.model.Weather

sealed class AppState {
    data class Success(val weatherData: List<Weather>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
