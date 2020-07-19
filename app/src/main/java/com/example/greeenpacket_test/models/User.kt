package com.example.greeenpacket_test.models

import android.content.Context
import com.example.greeenpacket_test.R
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class User : Serializable {
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

    /**
     * display name for this user
     * if preferFullName is not empty,
     * -> return preferredFullName
     * otherwise,
     * => return firstName and lastName
     */
    val displayName: String
        get() = preferredFullName ?: "$firstName ${lastName ?: ""}" ?: ""

    /**
     * a method display the age in String format
     * @param context [Context]
     */
    fun displayAge(context: Context): String {
        return "$employeeAge ${if (employeeAge == "1")
            context.getString(R.string.year_old) else context.getString(R.string.years_old)}"
    }

    /**
     * a method to check whether this user is the superior for the given user
     * @param user [User] model
     */
    fun isSuperiorFor(user: User): Boolean {
        return user != this && ((user.jobTitleName?.equals(
            this.jobTitleName,
            true
        ) ?: false && this.isTeamLead) || (user.isTeamLead && "General Manager".equals(
            this.jobTitleName,
            true
        )))
    }


}