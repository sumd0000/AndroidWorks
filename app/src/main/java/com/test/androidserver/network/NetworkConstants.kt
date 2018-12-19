package com.test.androidserver.network

import android.content.Context
import android.net.ConnectivityManager

 object NetworkConstants {
    var BASE_URL = "http://192.168.0.98/demotest/"

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return cm.activeNetworkInfo != null
    }

     fun isOnline(context: Context): Boolean {
         val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
         val netInfo = connectivityManager.activeNetworkInfo
         return netInfo != null && netInfo.isConnected
     }

}
