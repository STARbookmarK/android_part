package com.example.bookmarkkk

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bookmarkkk.databinding.FirstPageBinding
import org.koin.android.ext.android.inject

//앱 실행 시 가장 먼저 보게되는 화면으로, 로그인과 회원가입 메뉴 선택 가능(자동 로그인 설정 시 바로 메인화면으로 진입)
class FirstPage : Fragment(R.layout.first_page), OnClickListener{

    // no reflection API is used under the hood
    private val binding by viewBinding(FirstPageBinding::bind)
    private val viewModel : ViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener(this)
        binding.joinBtn.setOnClickListener(this)
    }

    override fun onStart() { // 자동 로그인(구글)위한 로그인 여부 확인
        super.onStart()
        runAutoLogin()
    }

    private fun runAutoLogin(){
        viewModel.runAutoLogin()
        viewModel.autoLoginValue.observe(viewLifecycleOwner, Observer { value ->
            if (value == 1)
                Navigation.findNavController(binding.root).navigate(R.id.main_to_mainPage_action)
        })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.loginBtn -> {
                Navigation.findNavController(binding.root).navigate(R.id.main_to_login_action)
            }
            R.id.joinBtn -> {
                Navigation.findNavController(binding.root).navigate(R.id.main_to_join_action)
            }
        }
    }

    companion object{
        const val TAG = "FirstPage"
    }
}