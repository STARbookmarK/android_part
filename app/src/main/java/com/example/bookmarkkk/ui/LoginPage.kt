package com.example.bookmarkkk.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bookmarkkk.DataStoreModule
import com.example.bookmarkkk.R
import com.example.bookmarkkk.api.model.LoginData
import com.example.bookmarkkk.api.request.AuthenticationService
import com.example.bookmarkkk.databinding.LoginBinding
import io.github.muddz.styleabletoast.StyleableToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import java.lang.Exception


class LoginPage : Fragment(R.layout.login), OnClickListener { //로그인 페이지

    private val binding by viewBinding(LoginBinding::bind)
    private val auth : AuthenticationService by inject()
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
                //val response = NetworkClient.authenticationService.login(data)
                val response = auth.login(data)
                if (response.isSuccessful){
                    StyleableToast.makeText(requireContext(), "login", R.style.loginToast).show()
                    withContext(Dispatchers.IO) {
                        infoSaveModule.setPassword(data.user_pw) // 비밀번호 저장
                    }
                    Navigation.findNavController(binding.root).navigate(R.id.login_to_main_action) // MainActivity 이동
                }else {
                    StyleableToast.makeText(requireContext(), "아이디 또는 비밀번호가 잘못되었습니다",
                        R.style.errorToast
                    ).show()
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