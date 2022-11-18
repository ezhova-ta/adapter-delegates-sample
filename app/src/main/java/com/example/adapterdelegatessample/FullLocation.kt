package com.example.adapterdelegatessample

data class FullLocation(
    val country: String,
    val city: String,
    val street: String,
    val buildingNumber: String,
    val apartmentNumber: Int
) : Location()