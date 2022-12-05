package com.example.bookmarkkk.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager

private const val BASE_URL = "http://dev.stark.r-e.kr/"

val networkModule = module {

    fun provideGson () = GsonBuilder().setLenient().create()

    fun provideOkHttpClient() = OkHttpClient().newBuilder()
        .cookieJar(JavaNetCookieJar(CookieManager()))
        .build()

    fun provideRetrofitInstance(client: OkHttpClient, gson: Gson) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .build()

    single {
        provideGson()
    }

    single {
        provideOkHttpClient()
    }

    single {
        provideRetrofitInstance(get(), get())
    }
}