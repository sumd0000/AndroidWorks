package com.test.androidserver.model.post

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SetFavResponse {

    @SerializedName("success")
    @Expose
    var success: Int? = null
    @SerializedName("msg")
    @Expose
    var msg: String? = null

}