package com.example.bookmarkkk

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import androidx.compose.runtime.Composable
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

class FirstPage : Fragment(){ //앱 실행 시 가장 먼저 보게되는 화면으로, 로그인과 회원가입 메뉴 선택 가능(자동 로그인 설정 시 바로 메인화면으로 진입)
    private lateinit var binding: FirstPageBinding
    //private val infoSaveModule : DataStoreModule by inject()

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
    }

    private fun autoLogin(){
        NetworkClient.authenticationService.autoLogin()
            .enqueue(object: Callback<UserId> {
                override fun onResponse(call: Call<UserId>, response: Response<UserId>){
                    if (response.isSuccessful.not()){
                        Log.e(TAG, response.toString())
                    }else{
                        Log.e(TAG, response.toString())
                        Navigation.findNavController(binding.root).navigate(R.id.main_to_mainPage_action)
                    }
                }
                override fun onFailure(call: Call<UserId>, t: Throwable){
                    Log.e(LoginPage.TAG, t.toString())
                }
            })
    }

    companion object{
        const val TAG = "FirstPage"
    }
}