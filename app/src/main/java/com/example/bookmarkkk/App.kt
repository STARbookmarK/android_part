package com.example.bookmarkkk

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    //private lateinit var datastore : DataStoreModule

    companion object{
        private lateinit var app : App
        //fun getInstance() : App = app
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        startKoin {
            androidContext(this@App)
            modules(module)
        }
        //datastore = DataStoreModule(this)
    }

    //fun getDataStore() : DataStoreModule = datastore
}