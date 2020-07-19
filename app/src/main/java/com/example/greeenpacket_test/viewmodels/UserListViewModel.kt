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

/**
 * Class to maintain all business logic for [User] List and
 * any logic needed by UserList View to interact with User data
 */
class UserListViewModel : ViewModel() {

    private val status: MutableLiveData<Status> = MutableLiveData()
    private val users: MutableLiveData<List<User>> by lazy {
        MutableLiveData<List<User>>().also {
            loadUsers()
        }
    }

    /**
     * access list of [User]
     */
    fun getUsers(): LiveData<List<User>> {
        return users
    }

    /**
     * access [Status]
     */
    fun getStatus(): LiveData<Status> {
        return status
    }

    /**
     * retrieve the users data from BE and trigger [Status] and [User] list event
     */
    fun loadUsers() {
        val call: Call<ApiResponse?>? = ApiClient.client?.create(UserService::class.java)!!.users()
        call!!.enqueue(object : Callback<ApiResponse?> {
            override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
                if (users.value?.isEmpty() != false)
                    status.postValue(Status.FAILED)
            }

            override fun onResponse(call: Call<ApiResponse?>, response: Response<ApiResponse?>) {
                val apiResponse: ApiResponse? = response.body()
                if (apiResponse?.code == 200L) {
                    users.postValue(apiResponse.users)
                    status.postValue(Status.SUCCESS)

                } else
                    status.postValue(Status.DENIED)
            }

        })
    }

    /**
     * a method to find the superior for given user,
     * if the found a superior from the [User] list,
     * -> return [User]
     * Otherwise,
     * -> return null
     */
    fun getSuperiorFor(user: User): User? {
        return users.value?.find { findUser -> findUser.isSuperiorFor(user) }
    }

}