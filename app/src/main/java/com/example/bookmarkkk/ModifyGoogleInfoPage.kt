package com.example.bookmarkkk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bookmarkkk.databinding.ModifyMyinfoGoogleBinding

class ModifyGoogleInfoPage : Fragment() {

    private lateinit var binding : ModifyMyinfoGoogleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ModifyMyinfoGoogleBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //서버랑 통신해서 닉네임 + 상태메세지 + 보기값 저장 및 가져오기
    }
}