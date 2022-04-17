package com.example.bookmarkkk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.bookmarkkk.databinding.MainNotCategorizedBinding

class MainPage : Fragment() {
    private lateinit var binding: MainNotCategorizedBinding
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= MainNotCategorizedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //카테고리화를 선택하지 않았을 때 화면(기본값)
        //기본은 리스트형
        //바둑형 선택시(서버와 통신하여 값 변경 확인) recyclerView의 레이아웃 매니저로 GridLayoutManager 전달

        //createFromResource(Context context, int textArrayResId, int textViewResId)
        //Creates a new ArrayAdapter from external resources
        context?.let {
            spinner= Spinner(it)
            binding.stateSpinner.adapter=spinner.stateSpinnerSet()
            binding.rankSpinner.adapter=spinner.rankSpinnerSet()
        }
    }
}