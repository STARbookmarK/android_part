package com.example.bookmarkkk

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.math.log

class ViewModel(
    private val repository: UserRepository
    ) : ViewModel() {

    var userData : MutableLiveData<UserInfo> = MutableLiveData()

    init {
        repository.getUser()
        userData = repository.userData
    }

    fun changeBio(info: String){
        repository.changeBio(info)
    }

}