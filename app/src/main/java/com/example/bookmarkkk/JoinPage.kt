package com.example.bookmarkkk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.bookmarkkk.databinding.JoinBinding

class JoinPage : Fragment() {

    private lateinit var binding: JoinBinding
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
            Navigation.findNavController(binding.root).navigate(R.id.join_to_main)
        }
    }
}