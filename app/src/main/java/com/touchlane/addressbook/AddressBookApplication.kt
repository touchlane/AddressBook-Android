package com.touchlane.addressbook

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.touchlane.addressbook.di.dbModule
import com.touchlane.addressbook.di.domainModule
import com.touchlane.addressbook.di.viewModelModule
import org.koin.android.ext.android.startKoin

class AddressBookApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin(this, listOf(dbModule, domainModule, viewModelModule))
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: Context
    }
}