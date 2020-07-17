package com.example.greeenpacket_test.resources

import com.example.greeenpacket_test.models.ApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface UserService{
    @GET("/data.json")
    fun users(): Call<ApiResponse?>?
}