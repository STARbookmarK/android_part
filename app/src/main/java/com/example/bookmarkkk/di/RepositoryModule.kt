package com.example.bookmarkkk.di

import com.example.bookmarkkk.repository.UserRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val repositoryModule = module {
    single {
        UserRepository(androidApplication(),
            get(),
            get())
    }
}