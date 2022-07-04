package com.example.bookmarkkk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response

class ViewModel : ViewModel() {

    private val repository by lazy { Repository() }
    lateinit var list : UserInfo

    fun getUser() : UserInfo{
        viewModelScope.launch {
            list = repository.getUser()
        }
        return list
    }
//
//    fun getUserInfo() : Response<UserInfo>{
//        return repository.getUserData()
//    }
//
}