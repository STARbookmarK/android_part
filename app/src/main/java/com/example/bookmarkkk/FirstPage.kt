package com.example.bookmarkkk

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.bookmarkkk.databinding.FirstPageBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FirstPage : Fragment(){
    private lateinit var binding: FirstPageBinding
    private val infoSaveModule : DataStoreModule by inject()
    //private val token by lazy { String }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FirstPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.main_to_login_action)
        }
        binding.joinBtn.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.main_to_join_action)
        }
    }

    override fun onStart() { // 자동 로그인(구글)위한 로그인 여부 확인
        super.onStart()
        autoLogin()
//        CoroutineScope(Dispatchers.IO).launch {
//            val token = infoSaveModule.refreshToken.first()
//            if(token.isNotEmpty()){
//                autoLogin() //자동 로그인에 체크하지 않앗어도 자동 로그인 실행
//            }
//        }
    }

    private fun autoLogin(){
        NetworkClient.autoLoginService.autoLogin()
            .enqueue(object: Callback<UserInfo> {
                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>){
                    if (response.isSuccessful.not()){
                        Log.e("FirstPage", response.toString())
                        return
                    }else{
                        response.body()?.let {
                            Log.e("FirstPage", it.name)
                            Log.e("FirstPage", it.id)
                            Log.e("FirstPage", it.tokenType)
                        }
                        Log.e("FirstPage", response.toString())
                        Navigation.findNavController(binding.root).navigate(R.id.main_to_mainPage_action)
                    }
                }
                override fun onFailure(call: Call<UserInfo>, t: Throwable){
                    Log.e(LoginPage.TAG, "연결 실패")
                    Log.e(LoginPage.TAG, t.toString())
                }
            })
    }
}