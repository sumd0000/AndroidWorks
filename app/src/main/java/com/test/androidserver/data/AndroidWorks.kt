package com.test.androidserver.data

import android.app.Application
import android.content.Context

class AndroidWorks : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

    }

    companion object {
        private var instance: AndroidWorks? = null

        fun getInstance(): AndroidWorks {
            return if (instance != null) {
                instance!!
            } else {
                AndroidWorks()
            }
        }

        val context: Context?
            get() = instance
    }
}

