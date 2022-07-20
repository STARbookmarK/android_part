package com.example.bookmarkkk

import org.koin.android.ext.koin.androidApplication
import org.koin.android.java.KoinAndroidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {
    single {
        DataStoreModule(androidApplication())
    }
}

val repositoryModule = module {
    single {
        UserRepository(androidApplication())
    }
}

val viewModelModule = module {
    viewModel {
        ViewModel(get())
    }
}