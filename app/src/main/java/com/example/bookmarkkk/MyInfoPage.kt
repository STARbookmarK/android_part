package com.example.bookmarkkk

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bookmarkkk.databinding.MyinfoBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyInfoPage : Fragment(R.layout.myinfo) {

    private val binding by viewBinding(MyinfoBinding::bind)
    private lateinit var spinner: Spinner
    private val viewModel : ViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 회원정보 조회
        viewModel.userData.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                binding.nickNameText.text = user.nickname
                binding.introText.text = user.info
            }
        })

        // 뷰 정렬방식(별점순, 최신순) 선택 스피너
        context?.let {
            spinner = Spinner(it)
            binding.rankSpinner.adapter = spinner.setRankSpinner()
        }

        binding.modifyBtn.setOnClickListener {
            val activity = activity as MainActivity
            activity.changeFragment(ModifyInfoPage())
        }
    }

    override fun onStart() {
        super.onStart()
    }
}