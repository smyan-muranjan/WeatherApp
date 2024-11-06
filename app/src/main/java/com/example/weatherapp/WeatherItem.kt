package com.example.weatherapp


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherItem(
    @SerialName("current")
    val current: Current,
    @SerialName("location")
    val location: Location
)