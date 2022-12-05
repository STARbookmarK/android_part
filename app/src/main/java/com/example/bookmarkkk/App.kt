package com.example.bookmarkkk

import com.example.bookmarkkk.di.networkModule
import com.example.bookmarkkk.di.apiModule
import com.example.bookmarkkk.di.repositoryModule
import com.example.bookmarkkk.di.viewModelModule
import com.example.bookmarkkk.di.dataStoreModule
import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    companion object{
        private lateinit var app : App
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        startKoin {
            androidContext(this@App)
            modules(
                apiModule,
                networkModule,
                repositoryModule,
                viewModelModule,
                dataStoreModule
            )
        }
    }
}