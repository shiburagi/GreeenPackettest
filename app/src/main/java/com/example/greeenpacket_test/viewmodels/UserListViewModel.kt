package com.example.greeenpacket_test.viewmodels

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

    init {


    }


    private val users: MutableLiveData<List<User>> by lazy {
        loadUsers()
        MutableLiveData<List<User>>()
    }

    fun getUsers(): LiveData<List<User>> {
        return users
    }
    private fun loadUsers() {
        val call: Call<ApiResponse?>? = ApiClient.client?.create(UserService::class.java)!!.users()
        call!!.enqueue(object : Callback<ApiResponse?> {
            override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
            }
            override fun onResponse(call: Call<ApiResponse?>, response: Response<ApiResponse?>) {
                val apiResponse: ApiResponse? = response.body()
                users.postValue(apiResponse?.users)
            }

        })
    }

}