package com.example.weatherapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _response = MutableStateFlow<WeatherItem?>(null)
    val response: StateFlow<WeatherItem?> get() = _response

    var location by mutableStateOf<String>("")
        private set

    fun updateLocation(newLocation: String) {
        location = newLocation
    }

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }


    private val key = ""
    private val baseUrl = "http://api.weatherapi.com/v1/current.json"
    fun doWork() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = client.get(baseUrl) {
                url {
                    parameters.append("key", key)
                    parameters.append("q", location)
                }
            }
            _response.value = response.body()
        }
    }
}