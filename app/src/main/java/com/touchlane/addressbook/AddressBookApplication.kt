package com.touchlane.addressbook

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class AddressBookApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: Context
    }
}