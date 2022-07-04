package com.example.bookmarkkk

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bookmarkkk.databinding.JoinBinding
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinPage : Fragment(R.layout.join), OnClickListener { // 회원가입 페이지

    private val binding by viewBinding(JoinBinding::bind)
    private var idCheckValue = 0
    private var nameCheckValue = 0
    private val viewModel : ViewModel by inject()

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
                // empty string check
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
                            register(id, pw, nickname, info)
                        }
                    }else{
                        binding.pwCheckShowText.text = "비밀번호가 일치하지 않습니다."
                        binding.pwCheckShowText.setTextColor(Color.RED)
                    }
                }
            }
        }
    }

    private fun register(id: String, pw: String, nickname: String, info: String){
        val data = SignUpData(id, pw, nickname, info)
        viewModel.join(data)
        viewModel.joinValue.observe(viewLifecycleOwner, Observer { value ->
            if (value == 1)
                Navigation.findNavController(binding.root).navigate(R.id.join_to_first)
        })
    }

    private fun idCheck(user_id : String){ // id 중복체크
        NetworkClient.signUpService.idCheck(user_id)
            .enqueue(object : Callback<IdCheckData>{
                override fun onResponse(call: Call<IdCheckData>, response: Response<IdCheckData>) {
                    if (response.isSuccessful.not()){
                        Log.e("id check", response.message())
                    }else{
                        response.body()?.let {
                            Log.i("id check", it.valid.toString())
                            if (it.valid){
                                binding.idCheckShowText.text = "사용 가능한 아이디입니다."
                                binding.idCheckShowText.setTextColor(Color.BLACK)
                                idCheckValue = 1
                            }else{
                                binding.idCheckShowText.text = "사용 불가능한 아이디입니다."
                                binding.idCheckShowText.setTextColor(Color.RED)
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<IdCheckData>, t: Throwable) {
                   Log.e("id check", t.toString())
                }
            })
    }

    private fun nickNameCheck(nickname : String) { // pw 중복체크
        NetworkClient.signUpService.nicknameCheck(nickname)
            .enqueue(object : Callback<NicknameCheckData>{
                override fun onResponse(call: Call<NicknameCheckData>, response: Response<NicknameCheckData>) {
                    if (response.isSuccessful.not()){
                        Log.e("nickname check", response.message())
                    }else{
                        response.body()?.let {
                            if (it.valid){
                                binding.nickCheckText.text = "사용 가능한 닉네임입니다."
                                binding.nickCheckText.setTextColor(Color.BLACK)
                                nameCheckValue = 1
                            }else{
                                binding.nickCheckText.text = "사용 불가능한 닉네임입니다."
                                binding.nickCheckText.setTextColor(Color.RED)
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<NicknameCheckData>, t: Throwable) {
                    Log.e("nickname check", t.toString())
                }
            })
    }

    companion object{
        const val TAG = "JoinPage"
    }
}