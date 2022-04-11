package com.example.bookmarkkk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bookmarkkk.databinding.MainCategorizedBinding

class MainCategorizedPage : Fragment() {
    lateinit var binding: MainCategorizedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= MainCategorizedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //카테고리화 선택시 보여줄 화면
    }
}