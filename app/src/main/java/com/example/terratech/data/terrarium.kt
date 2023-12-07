package com.example.terratech.data

data class Terrarium(
    val id: String,
    val name: String,
    val temperature: Int,
    val humidity: Int,
    val plants: List<String>,
)