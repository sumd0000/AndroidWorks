package com.test.androidserver.network

import com.test.androidserver.network.NetworkConstants.BASE_URL

object ApiUtils {

    val apiService: APIService
        get() = RetrofitClient.getClient(BASE_URL)!!.create(APIService::class.java)
}
