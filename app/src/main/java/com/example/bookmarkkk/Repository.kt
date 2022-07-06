package com.example.bookmarkkk

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import io.github.muddz.styleabletoast.StyleableToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(private val context: Context) {

    val userData : MutableLiveData<UserInfo> = MutableLiveData()

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

    fun changeBio(info: String) { // 소개글 변경
        NetworkClient.userInfoService.changeBio(BioOfUserInfo(info))
            .enqueue(object : Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful.not()){
                        Log.e(TAG, response.toString())
                    }else{
                        StyleableToast.makeText(context, "소개글 변경", R.style.bioToast).show()
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e(TAG, t.toString())
                }
            })
    }

    companion object{
        const val TAG = "REPOSITORY"
    }
}