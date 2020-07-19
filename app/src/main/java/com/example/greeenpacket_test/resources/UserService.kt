package com.example.greeenpacket_test.resources

import com.example.greeenpacket_test.models.ApiResponse
import retrofit2.Call
import retrofit2.http.GET

/**
 * Interface to register all endpoints
 */
interface UserService{
    /**
     * retrieve user data
     */
    @GET("/data.json")
    fun users(): Call<ApiResponse?>?
}