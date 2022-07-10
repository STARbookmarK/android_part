package com.example.bookmarkkk

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookmarkkk.databinding.AddTagDialogBinding
import io.github.muddz.styleabletoast.StyleableToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddBookmarkDialog : DialogFragment() {

    private lateinit var binding : AddTagDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddTagDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 목서버에 북마크 저장하기

        binding.bookmarkSaveBtn.setOnClickListener {
            val tag = binding.tagEdit.text.toString()
            val url = binding.urlEdit.text.toString()
            val info = binding.descriptionEdit.toString()
            val rate = binding.rateText.text.toString()

//            TestClient.bookmarkService.addBookmark(Bookmark(tag, url, info, rate)) // 뷰타입 지정
//                .enqueue(object: Callback<Void> {
//                    override fun onResponse(call: Call<Void>, response: Response<Void>){
//                        if (response.isSuccessful.not()){
//                            Log.e(TAG, response.toString())
//                        }else{
//                            context?.let { StyleableToast.makeText(it, "SAVE", R.style.saveToast).show() }
//                        }
//                    }
//                    override fun onFailure(call: Call<Void>, t: Throwable){
//                        Log.e(TAG, t.toString())
//                    }
//                })
        }
    }

    companion object{
        const val TAG = "AddBookmarkDialog"
    }
}