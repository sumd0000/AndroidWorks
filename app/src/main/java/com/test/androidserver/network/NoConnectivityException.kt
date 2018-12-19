package com.test.androidserver.network

import java.io.IOException

class NoConnectivityException : IOException() {

    override val message: String?
        get() = super.message

}