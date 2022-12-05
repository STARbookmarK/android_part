package com.example.bookmarkkk.di

import com.example.bookmarkkk.viewModel.ViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        ViewModel(get())
    }
}