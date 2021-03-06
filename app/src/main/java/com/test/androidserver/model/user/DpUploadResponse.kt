package com.test.androidserver.model.user


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DpUploadResponse {

    @SerializedName("success")
    @Expose
    var success: Int? = null
    @SerializedName("msg")
    @Expose
    var msg: String? = null

}