package com.example.greeenpacket_test.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ApiResponse {
    @SerializedName("code")
    @Expose
    var code: Long = 0

    @SerializedName("data")
    @Expose
    var users: List<User>? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
}