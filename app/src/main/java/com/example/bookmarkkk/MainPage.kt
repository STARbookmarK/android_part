package com.example.bookmarkkk

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookmarkkk.databinding.MainNotCategorizedBinding
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPage : Fragment(), View.OnClickListener {
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

        //태그 동적으로 추가
        binding.tagGroup.addView(Chip(context).apply {
            text = "ALL"
            isCloseIconVisible = true //x 버튼
            setTextColor(ContextCompat.getColorStateList(context, R.color.black))
            chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.lightGray)
            setOnCloseIconClickListener{ binding.tagGroup.removeView(this)}
        })

        binding.bookmarkAddBtn.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        Log.e(TAG, "카테고리 X")
        NetworkClient.userInfoService.getUserInfo() // 뷰타입 지정
            .enqueue(object: Callback<UserInfo> {
                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>){
                    if (response.isSuccessful.not()){
                        Log.e(TAG, response.toString())
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

    companion object{
        const val TAG = "MainPage"
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.bookmarkAddBtn -> {
                val dialog = AddBookmarkDialog()
                dialog.show(parentFragmentManager, "AddBookmarkDialog")
            }
        }
    }
}