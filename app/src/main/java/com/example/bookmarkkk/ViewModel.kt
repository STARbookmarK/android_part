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

class ViewModel(private val repository: Repository) : ViewModel() {

    var userData : MutableLiveData<UserInfo> = MutableLiveData()
    var loginResultValue : MutableLiveData<Int> = MutableLiveData()
    var autoLoginValue : MutableLiveData<Int> = MutableLiveData()
    var joinValue : MutableLiveData<Int> = MutableLiveData()

    init {
        repository.getUser()
        userData = repository.userData
    }

    fun login(data: LoginData) {
        repository.login(data)
        loginResultValue = repository.loginResult
    }

    fun runAutoLogin() {
        repository.runAutoLogin()
        autoLoginValue = repository.autoLoginValue
    }

    fun logout() {
        repository.logout()
        loginResultValue = repository.loginResult
    }

    fun join(data: SignUpData) {
        repository.join(data)
        joinValue = repository.joinValue
    }

}