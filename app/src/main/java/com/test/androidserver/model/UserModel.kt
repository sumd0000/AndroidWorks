package com.test.androidserver.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserModel {


    @SerializedName("success")
    @Expose
    private var success: Int? = null
    @SerializedName("msg")
    @Expose
    private var msg: String? = null
    @SerializedName("User")
    @Expose
    private var user: List<User>? = null

    fun getSuccess(): Int? {
        return success
    }

    fun setSuccess(success: Int?) {
        this.success = success
    }

    fun getMsg(): String? {
        return msg
    }

    fun setMsg(msg: String) {
        this.msg = msg
    }

    fun getUser(): List<User>? {
        return user
    }

    fun setUser(user: List<User>) {
        this.user = user
    }

}