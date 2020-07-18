package com.example.greeenpacket_test.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.greeenpacket_test.constants.Status
import com.example.greeenpacket_test.models.ApiResponse
import com.example.greeenpacket_test.models.User
import com.example.greeenpacket_test.resources.ApiClient
import com.example.greeenpacket_test.resources.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserListViewModel : ViewModel() {

    private val status: MutableLiveData<Status> = MutableLiveData()
    private val users: MutableLiveData<List<User>> by lazy {
        MutableLiveData<List<User>>().also {
            loadUsers()
        }
    }

    fun getUsers(): LiveData<List<User>> {
        return users
    }

    fun getStatus(): LiveData<Status> {
        return status
    }

    fun loadUsers() {
        val call: Call<ApiResponse?>? = ApiClient.client?.create(UserService::class.java)!!.users()
        call!!.enqueue(object : Callback<ApiResponse?> {
            override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
                status.postValue(Status.FAILED)

            }

            override fun onResponse(call: Call<ApiResponse?>, response: Response<ApiResponse?>) {
                val apiResponse: ApiResponse? = response.body()
                if (apiResponse?.code == 200L)
                    users.postValue(apiResponse.users)
                else
                    status.postValue(Status.DENIED)
            }

        })
    }

}