package com.example.myapplication

import retrofit2.Call
import retrofit2.http.GET

interface JSONApi {
    @GET("dc7304ee")
    fun getInfo(): Call<Any>
}