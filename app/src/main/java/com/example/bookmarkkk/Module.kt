package com.example.bookmarkkk

import com.google.gson.GsonBuilder
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.java.KoinAndroidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager

val module = module {
    single {
        DataStoreModule(androidApplication())
    }
}

private const val BASE_URL = "http://dev.stark.r-e.kr/"

val networkModule = module {
    single {
        OkHttpClient().newBuilder()
            .cookieJar(JavaNetCookieJar(CookieManager()))
            .build()
    }

    single {
        GsonBuilder().setLenient().create()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(ResultCallAdapterFactory.create())
            .build()
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