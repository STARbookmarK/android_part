package com.example.bookmarkkk

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bookmarkkk.databinding.ModifyInfoBinding
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModifyInfoPage : Fragment() {
    private lateinit var binding: ModifyInfoBinding
    private val userInfo : NetworkRepository by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= ModifyInfoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //서버랑 통신해서 닉네임 + 상태메세지 + 보기값 저장 및 가져오기
        //먼저 자동로그인이나 로그인 되면 서버랑 통신해서 사용자 정보값 다 가져온 다음에
        //뷰모델이나 DataStore에 저장해서 쓰기??
        //보기 방식은 굳이 서버와 통신하지 않아도 될 것 같음
        getUserInfo()
    }

    private fun getUserInfo() {
        NetworkClient.autoLoginService.autoLogin()
            .enqueue(object: Callback<UserInfo> {
                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>){
                    if (response.isSuccessful.not()){
                        Log.e("ModifyInfoPage", response.toString())
                        return
                    }else{
                        response.body()?.let {
                            binding.userId.text=it.id
                            binding.nickEditText.setText(it.name)
                        }
                    }
                }
                override fun onFailure(call: Call<UserInfo>, t: Throwable){
                    Log.e(LoginPage.TAG, "연결 실패")
                    Log.e(LoginPage.TAG, t.toString())
                }
            })
    }

}