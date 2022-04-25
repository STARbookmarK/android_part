package com.example.bookmarkkk

import android.app.Application

class App : Application() {

    private lateinit var datastore : EmailStoreModule

    companion object{
        private lateinit var app : App
        fun getInstance() : App = app
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        datastore = EmailStoreModule(this)
    }

    fun getDataStore() : EmailStoreModule = datastore
}