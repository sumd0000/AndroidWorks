package com.test.androidserver.model.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProfileResponse {

    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("full_name")
    @Expose
    var fullName: String? = null
    @SerializedName("email")
    @Expose
    var email: String? = null
    @SerializedName("password")
    @Expose
    var password: String? = null
    @SerializedName("address")
    @Expose
    var address: String? = null
    @SerializedName("city")
    @Expose
    var city: String? = null
    @SerializedName("country")
    @Expose
    var country: String? = null
    @SerializedName("postal_code")
    @Expose
    var postalCode: String? = null
    @SerializedName("ph_no")
    @Expose
    var phNo: String? = null
    @SerializedName("dp")
    @Expose
    var dp: String? = null
    @SerializedName("created_time")
    @Expose
    var createdTime: String? = null

}