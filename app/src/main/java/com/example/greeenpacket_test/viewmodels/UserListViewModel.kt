package com.example.greeenpacket_test.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.greeenpacket_test.models.ApiResponse
import com.example.greeenpacket_test.models.User
import com.example.greeenpacket_test.resources.ApiClient
import com.example.greeenpacket_test.resources.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserListViewModel : ViewModel() {
    private val usersLiveData =
        MutableLiveData<List<User>>()

    init {
        val call: Call<ApiResponse?>? = ApiClient.client?.create(UserService::class.java)!!.users()
        Log.e("User", "call")

        call!!.enqueue(object : Callback<ApiResponse?> {
            override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
                Log.e("User", t.message)

            }

            override fun onResponse(call: Call<ApiResponse?>, response: Response<ApiResponse?>) {
                val apiResponse: ApiResponse? = response.body()
                Log.e("User", apiResponse?.users?.size.toString())
                usersLiveData.postValue(apiResponse?.users)
            }

        })

    }

    val listenUsers: LiveData<List<User>>
        get() = usersLiveData

}