package com.example.bookmarkkk

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bookmarkkk.databinding.FirstPageBinding
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//앱 실행 시 가장 먼저 보게되는 화면으로, 로그인과 회원가입 메뉴 선택 가능(자동 로그인 설정 시 바로 메인화면으로 진입)
class FirstPage : Fragment(R.layout.first_page), OnClickListener{

    // no reflection API is used under the hood
    private val binding by viewBinding(FirstPageBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener(this)
        binding.joinBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.loginBtn -> { // 로그인 화면으로 이동
                Navigation.findNavController(binding.root).navigate(R.id.main_to_login_action)
            }
            R.id.joinBtn -> { // 회원가입 화면으로 이동
                Navigation.findNavController(binding.root).navigate(R.id.main_to_join_action)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        runAutoLogin()
    }

    private fun runAutoLogin(){ // 자동 로그인
        NetworkClient.authenticationService.autoLogin()
            .enqueue(object: Callback<UserId> {
                override fun onResponse(call: Call<UserId>, response: Response<UserId>){
                    if (response.isSuccessful){ // 자동 로그인 성공
                        Log.e(TAG, response.toString())
                        Navigation.findNavController(binding.root).navigate(R.id.main_to_mainPage_action)
                    }else{
                        Log.i(TAG, response.toString())
                    }
                }
                override fun onFailure(call: Call<UserId>, t: Throwable){
                    Log.e(TAG, t.toString())
                }
            })
    }

    companion object{
        const val TAG = "FirstPage"
    }
}