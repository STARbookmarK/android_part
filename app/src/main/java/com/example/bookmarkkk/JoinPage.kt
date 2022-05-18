package com.example.bookmarkkk

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.bookmarkkk.databinding.JoinBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinPage : Fragment() {

    private lateinit var binding : JoinBinding
    private lateinit var user_id : String
    private lateinit var user_pw : String
    private lateinit var nickName : String
    private lateinit var stateMessage : String

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
        // 일반 회원가입 페이지
        // 서버랑 통신해서 id, pw, 닉네임, 상태메세지 저장

        binding.joinOkBtn.setOnClickListener {
            //Navigation.findNavController(binding.root).navigate(R.id.join_to_main)
            register()
        }
    }

    private fun register(){
        user_id = binding.idEdit.text.toString()
        user_pw = binding.pwEdit.text.toString()
        nickName = binding.nicknameEdit.text.toString()
        stateMessage = binding.messageEdit.text.toString()

        RegisterClient.registerService.register(RegisterData(user_id, user_pw, nickName, stateMessage))
            .enqueue(object: Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful.not()){
                        Log.e("JoinPage", response.message())
                    }else{
                        Log.e("JoinPage", response.headers().toString())
                        Toast.makeText(context, "가입", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("JoinPage", t.toString())
                }

            })
    }
}