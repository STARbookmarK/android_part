package com.example.bookmarkkk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bookmarkkk.databinding.ModifyDialogBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookmarkModifyDialog( // 북마크 내용 수정하기
    private val data: BookmarkForModify
    ) : DialogFragment(), OnClickListener {

    private val binding by viewBinding(ModifyDialogBinding::bind)
    var list = mutableListOf("web", "blog") // 태그 추가 안됨, 얘는 임시태그
    private val viewModel : ViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.modify_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addRateBtn.setOnClickListener(this)
        binding.minusRateBtn.setOnClickListener(this)
        binding.bookmarkSaveBtn.setOnClickListener(this)

        with(binding) {
            titleEdit.setText(data.title)
            tagEdit.setText("") // 쪼갠 태그는 chip 형태로 저장
            descriptionEdit.setText(data.description)
            rateText.text = data.rate.toString()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addRateBtn -> {
                var rate = binding.rateText.text.toString().toInt()
                if (rate != 5){
                    rate += 1
                    binding.rateText.text = rate.toString()
                }
            }
            R.id.minusRateBtn -> {
                var rate = binding.rateText.text.toString().toInt()
                if (rate != 1){
                    rate -= 1
                    binding.rateText.text = rate.toString()
                }
            }

            R.id.bookmarkSaveBtn -> {
                // 특정 이모티콘은 제목에 추가가 안되는지 이모티콘 추가하면 제목 수정 못함
                val item = BookmarkForModify(
                    data.id,
                    binding.titleEdit.text.toString(),
                    data.address,
                    binding.descriptionEdit.text.toString(),
                    binding.rateText.text.toString().toInt(),
                    true,
                    data.hashtags
                )
                viewModel.modifyBookmark(item)
//                NetworkClient.bookmarkService.updateBookmark(
//                    BookmarkForModify(
//                        data.id,
//                        binding.titleEdit.text.toString(),
//                        data.address,
//                        binding.descriptionEdit.text.toString(),
//                        binding.rateText.text.toString().toInt(),
//                        true,
//                        data.hashtags
//                    )
//                ).enqueue(object : Callback<Void>{
//                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
//                        if (response.isSuccessful){
//                            Log.i("BookmarkDialog", response.code().toString())
//                        }else {
//                            Log.e("BookmarkDialog", response.code().toString())
//                        }
//                    }
//                    override fun onFailure(call: Call<Void>, t: Throwable) {
//                        Log.e("BookmarkDialog", t.toString())
//                    }
//                })
                this.dismiss()
                val intent = Intent(context, MainActivity::class.java) // 메인화면으로 이동
                startActivity(intent)
                // 추가 시 메인화면으로 이동해도 추가된 북마크가 바로 갱신이 안됨
            }
        }
    }
}