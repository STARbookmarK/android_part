package com.example.bookmarkkk.di

import com.example.bookmarkkk.DataStoreModule
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataStoreModule = module {
    single {
        DataStoreModule(androidApplication())
    }
}