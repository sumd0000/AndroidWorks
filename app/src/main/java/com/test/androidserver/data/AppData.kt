package com.test.androidserver.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.util.*

object AppData {
    //check internet
    fun isNetworkConnected(): Boolean {
        val connectivityManager = AndroidWorks.context?.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: true
        } else true
    }

 /*   val connectivityManager = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true*/

    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    fun getLanguages(id: Int):String
    {
        val lang_arr = ArrayList<String>()
        lang_arr.add("Bangla")
        lang_arr.add("English")
        return lang_arr.get(id)
    }

}
