package com.example.bookmarkkk

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bookmarkkk.databinding.LoginBinding
import io.github.muddz.styleabletoast.StyleableToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class LoginPage : Fragment(R.layout.login), OnClickListener { //로그인 페이지

    private val binding by viewBinding(LoginBinding::bind)
    private val infoSaveModule : DataStoreModule by inject()
    private lateinit var id : String
    private lateinit var pw : String
    private var autoLogin : Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.loginBtn -> {
                login()
            }
        }
    }

    // 결과값이 Void인 경우는 response를 쓰는게 좋을 듯 하다
    private fun login() {
        id = binding.idEditText.text.toString()
        pw = binding.pwEditText.text.toString()
        autoLogin = binding.loginCheckBox.isChecked
        val data = LoginData(id, pw, autoLogin)

        lifecycleScope.launch {
            try {
                val response = NetworkClient.authenticationService.login(data)
                if (response.isSuccessful){
                    StyleableToast.makeText(requireContext(), "login", R.style.loginToast).show()
                    withContext(Dispatchers.IO) {
                        infoSaveModule.setPassword(data.user_pw) // 비밀번호 저장
                    }
                    Navigation.findNavController(binding.root).navigate(R.id.login_to_main_action) // MainActivity 이동
                }else {
                    StyleableToast.makeText(requireContext(), "아이디 또는 비밀번호가 잘못되었습니다", R.style.errorToast).show()
                }
            }catch (e: Exception){
                Log.e(TAG, e.toString())
            }
        }

//        lifecycleScope.launch {
//            try {
//                NetworkClient.authenticationService.login(data)
//                StyleableToast.makeText(requireContext(), "login", R.style.loginToast).show()
//                withContext(Dispatchers.IO) {
//                    infoSaveModule.setPassword(data.user_pw) // 비밀번호 저장
//                }
//                Navigation.findNavController(binding.root).navigate(R.id.login_to_main_action) // MainActivity 이동
//            }catch (e: Exception){
//                Log.e(TAG, e.toString())
//            }
//        }
    }

    companion object{
        const val TAG = "LoginPage"
    }
}