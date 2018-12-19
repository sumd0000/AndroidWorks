package com.test.androidserver.model.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserProfile {

    @SerializedName("success")
    @Expose
    var success: Int? = null
    @SerializedName("users")
    @Expose
    var users: List<ProfileResponse>? = null
    @SerializedName("msg")
    @Expose
    var msg: String? = null

}