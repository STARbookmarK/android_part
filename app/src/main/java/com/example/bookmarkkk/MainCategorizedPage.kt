package com.example.bookmarkkk

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookmarkkk.databinding.MainCategorizedBinding
import com.google.android.material.chip.Chip
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainCategorizedPage : Fragment(), View.OnClickListener {
    lateinit var binding: MainCategorizedBinding
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= MainCategorizedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //카테고리화 선택시 보여줄 화면
        //스피너, 태그 동적으로 추가해야함

        context?.let {
            spinner= Spinner(it)
            binding.stateSpinner.adapter=spinner.stateSpinnerSet()
            binding.rankSpinner.adapter=spinner.rankSpinnerSet()
        }

        //태그
        binding.tagGroup.addView(Chip(context).apply {
            text = "태그1"
            isCloseIconVisible = true //x 버튼
            setTextColor(ContextCompat.getColorStateList(context, R.color.black))
            chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.lightGray)
            setOnCloseIconClickListener{ binding.tagGroup.removeView(this)}
        })

        binding.bookmarkAddBtn.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        NetworkClient.userInfoService.getUserInfo() // 북마크 보기방식 지정
            .enqueue(object: Callback<UserInfo> {
                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>){
                    if (response.isSuccessful.not()){
                        Log.e(MainPage.TAG, response.toString())
                    }else{
                        response.body()?.let {
                            if (it.bookmarkShow==1){ // 리스트형
                                binding.bookmarkView.layoutManager = LinearLayoutManager(context)
                            }else{ // 격자형
                                binding.bookmarkView.layoutManager = GridLayoutManager(context, 2)
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<UserInfo>, t: Throwable){
                    Log.e(TAG, t.toString())
                }
            })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.bookmarkAddBtn -> {
                val dialog = AddBookmarkDialog()
                dialog.show(parentFragmentManager, "AddBookmarkDialog")
            }
        }
    }

    companion object{
        const val TAG = "MainCategorizedPage"
    }
}