package com.test.androidserver.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

public class User {
    @SerializedName("id")
    @Expose
    private var id: String? = null
    @SerializedName("full_name")
    @Expose
    private var fullName: String? = null
    @SerializedName("email")
    @Expose
    private var email: String? = null
    @SerializedName("password")
    @Expose
    private var password: String? = null
    @SerializedName("address")
    @Expose
    private var address: String? = null
    @SerializedName("city")
    @Expose
    private var city: String? = null
    @SerializedName("country")
    @Expose
    private var country: String? = null
    @SerializedName("postal_code")
    @Expose
    private var postalCode: String? = null
    @SerializedName("ph_no")
    @Expose
    private var phNo: String? = null
    @SerializedName("dp")
    @Expose
    private var dp: String? = null
    @SerializedName("created_time")
    @Expose
    private var createdTime: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getFullName(): String? {
        return fullName
    }

    fun setFullName(fullName: String) {
        this.fullName = fullName
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun getAddress(): String? {
        return address
    }

    fun setAddress(address: String) {
        this.address = address
    }

    fun getCity(): String? {
        return city
    }

    fun setCity(city: String) {
        this.city = city
    }

    fun getCountry(): String? {
        return country
    }

    fun setCountry(country: String) {
        this.country = country
    }

    fun getPostalCode(): String? {
        return postalCode
    }

    fun setPostalCode(postalCode: String) {
        this.postalCode = postalCode
    }

    fun getPhNo(): String? {
        return phNo
    }

    fun setPhNo(phNo: String) {
        this.phNo = phNo
    }

    fun getDp(): String? {
        return dp
    }

    fun setDp(dp: String) {
        this.dp = dp
    }

    fun getCreatedTime(): String? {
        return createdTime
    }

    fun setCreatedTime(createdTime: String) {
        this.createdTime = createdTime
    }

}