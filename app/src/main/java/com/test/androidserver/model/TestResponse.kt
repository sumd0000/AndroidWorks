package com.test.androidserver.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TestResponse {

    @SerializedName("success")
    @Expose
    var success: Int? = null
    @SerializedName("msg")
    @Expose
    var msg: String? = null

}