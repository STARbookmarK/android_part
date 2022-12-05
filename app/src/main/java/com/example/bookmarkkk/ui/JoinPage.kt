package com.example.bookmarkkk.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bookmarkkk.R
import com.example.bookmarkkk.api.request.SignUpService
import com.example.bookmarkkk.api.model.SignUpData
import com.example.bookmarkkk.databinding.JoinBinding
import io.github.muddz.styleabletoast.StyleableToast
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.lang.Exception

class JoinPage : Fragment(R.layout.join), OnClickListener { // 회원가입 페이지

    private val binding by viewBinding(JoinBinding::bind)
    private val signUp : SignUpService by inject()
    private var idCheckValue = 0 // id 중복체크를 위한 변수
    private var nameCheckValue = 0 // 닉네임 중복체크를 위한 변수

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.idOkBtn.setOnClickListener(this)
        binding.nicknameOkBtn.setOnClickListener(this)
        binding.joinOkBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.idOkBtn -> {
                idCheck(binding.idEdit.text.toString())
            }
            R.id.nicknameOkBtn -> {
                nickNameCheck(binding.nicknameEdit.text.toString())
            }
            R.id.joinOkBtn -> {
                val id = binding.idEdit.text.toString()
                val pw = binding.pwEdit.text.toString()
                val pwCheck = binding.pwCheckEdit.text.toString()
                val nickname = binding.nicknameEdit.text.toString()
                val info = binding.messageEdit.text.toString()

                if (id == "" || pw == "" || pwCheck == "" || nickname == ""){ // 필수 입력값 중 하나라도 입력 안되었을 경우
                    Toast.makeText(context, "입력 정보를 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
                } else{ // 모든 값이 입력된 경우
                    if (pw == pwCheck){ // 비밀번호 일치 시
                        if (idCheckValue == 0 || nameCheckValue == 0){ // 아이디, 닉네임 중복체크 안했을 경우
                            Toast.makeText(context, "아이디 또는 닉네임은 중복확인이 필수입니다.", Toast.LENGTH_SHORT).show()
                        }else { // 입력값, 비밀번호 확인, 중복체크 모두 완료했을 경우 가입절차 실행
                            val data = SignUpData(id, pw, nickname, info)
                            register(data)
                        }
                    }else{
                        binding.pwCheckShowText.text = "비밀번호가 일치하지 않습니다."
                        binding.pwCheckShowText.setTextColor(Color.RED)
                    }
                }
            }
        }
    }

    private fun register(data: SignUpData){
        lifecycleScope.launch {
            try {
                //val response = NetworkClient.signUpService.signUp(data)
                val response = signUp.signUp(data)
                if (response.isSuccessful){
                    StyleableToast.makeText(requireContext(), "가입되었습니다. 다시 로그인 해주세요",
                        R.style.joinToast
                    ).show()
                    Navigation.findNavController(binding.root).navigate(R.id.join_to_first)
                }else {
                    Toast.makeText(context, "아이디 또는 닉네임은 중복확인이 필수입니다.", Toast.LENGTH_SHORT).show()
                }
            }catch (e: Exception){
                Log.e(TAG, e.toString())
            }
        }
//        NetworkClient.signUpService.signUp(data)
//            .enqueue(object: Callback<Void> {
//                override fun onResponse(call: Call<Void>, response: Response<Void>) {
//                    if (response.isSuccessful){ // 이미 존재하는 아이디 또는 닉네임을 입력했을 경우
//                        StyleableToast.makeText(requireContext(), "가입되었습니다. 다시 로그인 해주세요", R.style.joinToast).show()
//                        Navigation.findNavController(binding.root).navigate(R.id.join_to_first)
//                    }else{
//                        Toast.makeText(context, "아이디 또는 닉네임은 중복확인이 필수입니다.", Toast.LENGTH_SHORT).show()
//                    }
//                }
//                override fun onFailure(call: Call<Void>, t: Throwable) {
//                    Log.e(TAG, t.toString() )
//                }
//            })
    }

    // response보다 result를 사용하는게 서버와의 통신 결과 성공 유무를 나타내는데 더 직관적인 듯 하다!!
    private fun idCheck(user_id : String){ // id 중복체크
        lifecycleScope.launch {
            //val result = NetworkClient.signUpService.idCheck(user_id)
            val result = signUp.idCheck(user_id)
            if (result.isSuccess) {
                val body = result.getOrNull()
                body?.let {
                    if (it.valid) {
                        binding.idCheckShowText.text = "사용 가능한 아이디입니다."
                        binding.idCheckShowText.setTextColor(Color.BLACK)
                        idCheckValue = 1
                    } else {
                        binding.idCheckShowText.text = "사용 불가능한 아이디입니다."
                        binding.idCheckShowText.setTextColor(Color.RED)
                    }
                }
            } else {
                Log.e(TAG, result.toString())
            }
        }

//            lifecycleScope.launch {
//                try {
//                    val response = NetworkClient.signUpService.idCheck(user_id)
//                    val body = response.body()
//                    body?.let {
//                        //
//                    }
//                }catch (e: Exception) {
//                    Log.e(TAG, e.toString())
//                }
//            }
    }

    private fun nickNameCheck(nickname : String) { // 닉네임 중복체크
        lifecycleScope.launch {
            //val result = NetworkClient.signUpService.nicknameCheck(nickname)
            val result = signUp.nicknameCheck(nickname)
            if (result.isSuccess) {
                val body = result.getOrNull()
                body?.let {
                    if (it.valid) {
                        binding.nickCheckText.text = "사용 가능한 닉네임입니다."
                        binding.nickCheckText.setTextColor(Color.BLACK)
                        nameCheckValue = 1
                    }else {
                        binding.nickCheckText.text = "사용 불가능한 닉네임입니다."
                        binding.nickCheckText.setTextColor(Color.RED)
                    }
                }
            }else {
                Log.e(TAG, result.toString())
            }
        }
    }

    companion object{
        const val TAG = "JoinPage"
    }
}