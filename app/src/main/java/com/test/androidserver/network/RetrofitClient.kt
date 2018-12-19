package com.test.androidserver.network

import com.test.androidserver.data.AndroidWorks.Companion.context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitClient {
    //var interceptor = HttpLoggingInterceptor()
    var client = OkHttpClient.Builder()
            .addInterceptor(NetworkConnectionInterceptor(context!!))
            .build()
    /*val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()*/  //for catch error
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit? {
        if (retrofit == null) {

           /* val client = OkHttpClient.Builder()
                    .addInterceptor(NetworkConnectionInterceptor(context))
                    .build()*/

            retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl).client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit
    }
}
