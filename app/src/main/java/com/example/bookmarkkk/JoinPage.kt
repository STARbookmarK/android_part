package com.example.bookmarkkk

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TimeFormatException
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.bookmarkkk.databinding.JoinBinding
import io.github.muddz.styleabletoast.StyleableToast
import kotlinx.coroutines.flow.combineTransform
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinPage : Fragment(), View.OnClickListener { // 회원가입 페이지

    private lateinit var binding : JoinBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= JoinBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 아이디 / 닉네임 중복체크는 API 변경으로 추후에 수정 예정
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
                if (id == "" || pw == "" || pwCheck == "" || nickname == ""){
                    Toast.makeText(context, "입력 정보를 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
                } else{
                    if (pw == pwCheck){ // 비밀번호 확인 ok
                        register(id, pw, nickname, info)
                    }else{
                        binding.pwCheckShowText.text = "비밀번호가 일치하지 않습니다."
                        binding.pwCheckShowText.setTextColor(Color.RED)
                    }
                }
            }
        }
    }

    private fun register(user_id: String, user_pw: String, nickname: String, info: String){
        NetworkClient.signUpService.signUp(SignUpData(user_id, user_pw, nickname, info))
            .enqueue(object: Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful.not()){
                        Log.e("register error", response.code().toString())
                        Toast.makeText(context, "아이디 또는 닉네임은 중복확인이 필수입니다.", Toast.LENGTH_SHORT).show()
                    }else{
                        Navigation.findNavController(binding.root).navigate(R.id.join_to_first)
                        context?.let { StyleableToast.makeText(it, "가입되었습니다. 다시 로그인 해주세요", R.style.joinToast).show() }
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("register error", t.toString() )
                }
            })
    }

    private fun idCheck(user_id : String){
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

    private fun nickNameCheck(nickname : String) {
        NetworkClient.signUpService.nicknameCheck(nickname)
            .enqueue(object : Callback<NicknameCheckData>{
                override fun onResponse(call: Call<NicknameCheckData>, response: Response<NicknameCheckData>) {
                    if (response.isSuccessful.not()){
                        Log.i("nickname check", response.message())
                    }else{
                        response.body()?.let {
                            if (it.valid){
                                binding.nickCheckText.text = "사용 가능한 닉네임입니다."
                                binding.nickCheckText.setTextColor(Color.BLACK)
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