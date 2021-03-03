package com.example.androiddevchallenge.data

import androidx.annotation.DrawableRes

data class Pet(
    val name: String,
    @DrawableRes val imageRes: Int,
    val personality: String = "",
    val requirement: String = "",
    val arrivedAt: String = "",
    val age: String = ""
)
