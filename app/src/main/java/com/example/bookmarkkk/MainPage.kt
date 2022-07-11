package com.example.bookmarkkk

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bookmarkkk.databinding.MainNotCategorizedBinding
import com.google.android.material.chip.Chip
import io.github.muddz.styleabletoast.StyleableToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPage : Fragment(R.layout.main_not_categorized), OnClickListener { //카테고리화를 선택하지 않았을 때 화면(기본값)

    private val binding by viewBinding(MainNotCategorizedBinding::bind)
    private lateinit var spinner: Spinner

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = BookMarkAdapter(requireContext())
        val list = ArrayList<Bookmark>()
        binding.listTypeBtn.setOnClickListener(this)
        binding.gridTypeBtn.setOnClickListener(this)
        binding.bookmarkAddBtn.setOnClickListener(this)

        context?.let {
            spinner= Spinner(it)
            binding.stateSpinner.adapter=spinner.stateSpinnerSet()
            binding.rankSpinner.adapter=spinner.rankSpinnerSet()
        }

        //태그 동적으로 추가
        binding.tagGroup.addView(Chip(context).apply {
            text = "ALL"
            isCloseIconVisible = true // x버튼
            setTextColor(ContextCompat.getColorStateList(context, R.color.black))
            chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.lightGray)
            setOnCloseIconClickListener{ binding.tagGroup.removeView(this)}
        })


        NetworkClient.userInfoService.getUserInfo() // 북마크 보기방식 지정
            .enqueue(object: Callback<UserInfo> {
                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>){
                    if (response.isSuccessful){
                        response.body()?.let {
                            if (it.bookmarkShow==1){ // 리스트형
                                binding.bookmarkView.layoutManager = LinearLayoutManager(context)
                            }else{ // 격자형
                                binding.bookmarkView.layoutManager = GridLayoutManager(context, 2)
                            }
                        }
                    }else{
                        Log.e(TAG, response.toString())
                    }
                }
                override fun onFailure(call: Call<UserInfo>, t: Throwable){
                    Log.e(TAG, t.toString())
                }
            })

        // 북마크 조회
        NetworkClient.bookmarkService.getAllBookmarks()
            .enqueue(object : Callback<List<Bookmark>> {
                override fun onResponse(call: Call<List<Bookmark>>, response: Response<List<Bookmark>>) {
                    if (response.isSuccessful){
                        response.body()?.let {
                            list.addAll(it)
                            adapter.addAll(list) // 어댑터에 데이터 추가
                            binding.bookmarkView.adapter = adapter // recyclerView, adapter 연결
                        }
                    }
                }
                override fun onFailure(call: Call<List<Bookmark>>, t: Throwable) {
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
            R.id.list_type_btn -> {
                binding.bookmarkView.layoutManager = LinearLayoutManager(context)
            }
            R.id.grid_type_btn -> {
                binding.bookmarkView.layoutManager = GridLayoutManager(context, 2)
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    companion object{
        const val TAG = "MainPage"
    }
}