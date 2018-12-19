package com.test.androidserver.data

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.test.androidserver.model.UserModel

class AppPreferenceHelper(context: Context) {
    private val appSharedPrefs: SharedPreferences
    private val prefsEditor: SharedPreferences.Editor

    init {
        this.appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE)
        this.prefsEditor = appSharedPrefs.edit()
    }

    fun clearPref() {
        prefsEditor.clear().commit()
    }

    companion object {
        private val APP_SHARED_PREFS = "com.test.androidserver"
        private var instance: AppPreferenceHelper? = null

        fun getInstance(): AppPreferenceHelper {
            if (instance is AppPreferenceHelper) {
                return instance as AppPreferenceHelper
            } else {
                instance = AppPreferenceHelper(AndroidWorks.getInstance().baseContext)
                return instance as AppPreferenceHelper
            }
        }
    }

    fun saveUserDetails(userModel: UserModel) {
        val gson = Gson()
        val json = gson.toJson(userModel)
        prefsEditor.putString("UserDetails", json)
        prefsEditor.commit()
    }

    fun getUserDetails(): UserModel? {
        val gson = Gson()
        val json = appSharedPrefs.getString("UserDetails", "")
        return gson.fromJson<Any>(json, UserModel::class.java) as UserModel?
    }
}
