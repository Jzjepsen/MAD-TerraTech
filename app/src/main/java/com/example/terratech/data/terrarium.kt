package com.example.terratech.data

data class Terrarium(
    val id: String,
    val name: String,
    val temperature: String,
    val humidity: String,
    val plants: List<String>,
)