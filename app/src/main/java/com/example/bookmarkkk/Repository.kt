package com.example.bookmarkkk

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import io.github.muddz.styleabletoast.StyleableToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(private val infoSaveModule : DataStoreModule, private val context: Context){

    val userData : MutableLiveData<UserInfo> = MutableLiveData()
    val loginResult : MutableLiveData<Int> = MutableLiveData() // 로그아웃 시 다시 0으로 셋팅
    val autoLoginValue : MutableLiveData<Int> = MutableLiveData()
    val joinValue : MutableLiveData<Int> = MutableLiveData()

    fun getUser(){
        NetworkClient.userInfoService.getUserInfo()
            .enqueue(object: Callback<UserInfo> {
                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>){
                    response.body()?.let {
                        userData.value = it
                    }
                }
                override fun onFailure(call: Call<UserInfo>, t: Throwable){
                    Log.e(TAG, t.toString())
                }
            })
    }

    fun login(data: LoginData) {//서버 통신 부분은 나중에 repository에 분리
        NetworkClient.authenticationService.login(data)
            .enqueue(object: Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>){
                    if (response.isSuccessful){ // 로그인 성공(일단 토스트 먼저 띄우기)
                        StyleableToast.makeText(context, "login", R.style.loginToast).show()
                        loginResult.value = 1
                        CoroutineScope(Dispatchers.IO).launch {
                            infoSaveModule.setPassword(data.user_pw)
                        }
                    }else{
                        StyleableToast.makeText(context, "아이디 또는 비밀번호가 잘못되었습니다", R.style.errorToast).show()
                        loginResult.value = 0
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable){
                    loginResult.value = 0
                    Log.e("$TAG -> login", t.toString())
                }
            })
    }

    fun runAutoLogin(){
        NetworkClient.authenticationService.autoLogin()
            .enqueue(object: Callback<UserId> {
                override fun onResponse(call: Call<UserId>, response: Response<UserId>){
                    if (response.isSuccessful){ // 자동 로그인 성공
                        Log.e(TAG, response.toString())
                        autoLoginValue.value = 1
                    }else{
                        Log.i(TAG, response.toString())
                        autoLoginValue.value = 0
                    }
                }
                override fun onFailure(call: Call<UserId>, t: Throwable){
                    Log.e(TAG, t.toString())
                    autoLoginValue.value = 0
                }
            })
    }

    fun logout() {
        NetworkClient.authenticationService.logout()
            .enqueue(object : Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful){ // 로그아웃 성공(일단 토스트 먼저 띄우기)
                        StyleableToast.makeText(context, "로그아웃", R.style.logoutToast).show()
                        loginResult.value = 0
                    }else {
                        StyleableToast.makeText(context, "로그아웃 실패", R.style.errorToast).show()
                        Log.e(TAG, response.toString())
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e(TAG, t.toString())
                }
            })
    }

    fun join(data: SignUpData) {
        NetworkClient.signUpService.signUp(data)
            .enqueue(object: Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful){ // 이미 존재하는 아이디 또는 닉네임을 입력했을 경우
                        StyleableToast.makeText(context, "가입되었습니다. 다시 로그인 해주세요", R.style.joinToast).show()
                        joinValue.value = 1
                    }else{
                        Toast.makeText(context, "아이디 또는 닉네임은 중복확인이 필수입니다.", Toast.LENGTH_SHORT).show()
                        joinValue.value = 0
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("register error", t.toString() )
                    joinValue.value = 0
                }
            })
    }

    companion object{
        const val TAG = "REPOSITORY"
    }
}