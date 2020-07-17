package com.example.greeenpacket_test.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class User : Serializable{
    @SerializedName("duties")
    @Expose
    var duties: Any? = null

    @SerializedName("emailAddress")
    @Expose
    var emailAddress: String? = null

    @SerializedName("employeeAge")
    @Expose
    var employeeAge: String? = null

    @SerializedName("employeeCode")
    @Expose
    var employeeCode: Any? = null

    @SerializedName("firstName")
    @Expose
    var firstName: String? = null

    @SerializedName("isTeamLead")
    @Expose
    var isTeamLead = false

    @SerializedName("jobTitleName")
    @Expose
    var jobTitleName: String? = null

    @SerializedName("lastName")
    @Expose
    var lastName: String? = null

    @SerializedName("phoneNumber")
    @Expose
    var phoneNumber: String? = null

    @SerializedName("preferredFullName")
    @Expose
    var preferredFullName: String? = null

    @SerializedName("profileImage")
    @Expose
    var profileImage: String? = null

    @SerializedName("region")
    @Expose
    var region: String? = null

    @SerializedName("userId")
    @Expose
    var userId: String? = null
}