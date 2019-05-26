package com.arkadr.wildcamper

data class Location (val latitude: Double, val longitude: Double) {

    init {
        require(latitude >= -90.0 && latitude <= 90.0) {"Latitude must be between (-90, 90)"}
        require(longitude >= -90.0 && longitude <= 90.0) {"Longitude must be between (-180, 180)"}
    }

}