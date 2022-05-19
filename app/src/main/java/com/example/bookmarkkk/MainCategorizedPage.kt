package com.example.bookmarkkk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.bookmarkkk.databinding.MainCategorizedBinding
import com.google.android.material.chip.Chip

class MainCategorizedPage : Fragment() {
    lateinit var binding: MainCategorizedBinding
    private lateinit var spinner: Spinner

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

        context?.let {
            spinner= Spinner(it)
            binding.stateSpinner.adapter=spinner.stateSpinnerSet()
            binding.rankSpinner.adapter=spinner.rankSpinnerSet()
        }

        //태그 동적으로 추가
        binding.tagGroup.addView(Chip(context).apply {
            text = "태그1"
            isCloseIconVisible = true //x 버튼
            setTextColor(ContextCompat.getColorStateList(context, R.color.black))
            chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.lightGray)
            setOnCloseIconClickListener{ binding.tagGroup.removeView(this)}
        })
    }
}