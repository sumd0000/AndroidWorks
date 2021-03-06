package com.test.androidserver.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CreatePostModel {

    @SerializedName("success")
    @Expose
    var success: Int? = null
    @SerializedName("msg")
    @Expose
    var msg: String? = null

}