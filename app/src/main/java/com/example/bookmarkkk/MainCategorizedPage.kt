package com.example.bookmarkkk

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bookmarkkk.databinding.MainCategorizedBinding
import com.google.android.material.chip.Chip
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainCategorizedPage : Fragment(R.layout.main_categorized), OnClickListener {
    private val binding by viewBinding(MainCategorizedBinding::bind,
    onViewDestroyed = { binding ->
        binding.bookmarkView.adapter = null
    })
    private lateinit var spinner: Spinner
    private val viewModel : ViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bookmarkAddBtn.setOnClickListener(this)

        val adapter = BookMarkAdapter(requireContext())
        val list = arrayListOf("java", "kotlin", "android", "spring", "react") // 태그 예시

        // 뷰 정렬방식(별점순, 최신순) 선택 스피너
        context?.let {
            spinner = Spinner(it)
            binding.rankSpinner.adapter = spinner.setRankSpinner()
        }

        //태그 동적으로 추가
        list.forEach { tagName ->
            binding.tagGroup.addView(
                createTagChip(
                    requireContext(),
                    tagName)
            )
        }

        // 북마크 클릭 시 해당 주소로 이동
        adapter.itemClick = object : BookMarkAdapter.ItemClick{
            override fun onClick(v: View, pos: Int, list: ArrayList<Bookmark>) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(list[pos].address))
                startActivity(intent)
            }
        }

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
        viewModel.bookmarkList.observe(viewLifecycleOwner, Observer { items ->
            adapter.removeAll()
            adapter.add(items)
            binding.bookmarkView.adapter = adapter
        })
    }

    private fun createTagChip(context: Context, tagName: String): Chip {
        return Chip(context).apply {
            text = tagName
            isCloseIconVisible = true // x버튼
            setTextColor(ContextCompat.getColorStateList(context, R.color.black))
            chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.lightGray)
            setOnCloseIconClickListener{ binding.tagGroup.removeView(this)}
        }
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
//        NetworkClient.userInfoService.getUserInfo() // 북마크 보기방식 지정
//            .enqueue(object: Callback<UserInfo> {
//                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>){
//                    if (response.isSuccessful){
//                        response.body()?.let {
//                            if (it.bookmarkShow==1){ // 리스트형
//                                binding.bookmarkView.layoutManager = LinearLayoutManager(context)
//                            }else{ // 격자형
//                                binding.bookmarkView.layoutManager = GridLayoutManager(context, 2)
//                            }
//                        }
//                    }else{
//                        Log.e(TAG, response.toString())
//                    }
//                }
//                override fun onFailure(call: Call<UserInfo>, t: Throwable){
//                    Log.e(TAG, t.toString())
//                }
//            })
    }

    companion object{
        const val TAG = "MainCategorizedPage"
    }
}