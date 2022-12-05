package com.example.bookmarkkk.di

import com.example.bookmarkkk.api.request.AuthenticationService
import com.example.bookmarkkk.api.request.SignUpService
import com.example.bookmarkkk.api.request.UserInfoService
import com.example.bookmarkkk.api.request.BookmarkService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    fun provideAuthenticationApi(retrofit: Retrofit) = retrofit.create(AuthenticationService::class.java)
    fun provideSignUpApi(retrofit: Retrofit) = retrofit.create(SignUpService::class.java)
    fun provideUserInfoApi(retrofit: Retrofit) = retrofit.create(UserInfoService::class.java)
    fun provideBookmarkApi(retrofit: Retrofit) = retrofit.create(BookmarkService::class.java)

    single {
        provideAuthenticationApi(get())
    }

    single {
        provideSignUpApi(get())
    }

    single {
        provideUserInfoApi(get())
    }

    single {
        provideBookmarkApi(get())
    }
}