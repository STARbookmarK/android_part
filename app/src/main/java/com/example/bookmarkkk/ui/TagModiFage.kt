package com.example.bookmarkkk.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.bookmarkkk.R
import com.example.bookmarkkk.databinding.TagModifyBinding

class TagModiFage : Fragment(){
    private lateinit var binding : TagModifyBinding
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= TagModifyBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val states = arrayListOf("기말고사", "코딩테스트") // 즐겨찾기 상태 예시

        // 즐겨찾기 상태 스피너 동적으로 추가
        val stateAdapter = ArrayAdapter(requireContext(), R.layout.state_spinner_style, states)
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.stateSpinner.adapter = stateAdapter

        binding.backToPageBtn.setOnClickListener {
            val activity = activity as MainActivity
            activity.changeFragment(TagPage())
        }
    }
}