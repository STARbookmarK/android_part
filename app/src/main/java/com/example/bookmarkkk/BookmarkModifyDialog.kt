package com.example.bookmarkkk

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bookmarkkk.databinding.MenuDialogBinding
import com.example.bookmarkkk.databinding.ModifyDialogBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookmarkModifyDialog() : DialogFragment(), OnClickListener {

    private val binding by viewBinding(ModifyDialogBinding::bind)
    private val viewModel : ViewModel by viewModel()
    var m_bookmarkId = 0
    var m_tags = ""
    var m_description = ""
    var m_rate = 0
    var list = mutableListOf<String>("web", "blog")

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

        binding.tagEdit.setText(m_tags)
        binding.descriptionEdit.setText(m_description)
        binding.rateText.text = m_rate.toString()
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
                NetworkClient.bookmarkService.updateBookmark(
                    BookmarkForModify(
                        m_bookmarkId,
                        "",
                        "",
                        m_description,
                        m_rate,
                        true,
                       list
                    )
                ).enqueue(object : Callback<Void>{
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful){
                            Log.e("BookmarkDialog", response.code().toString())
                        }else {
                            Log.e("BookmarkDialog", response.code().toString())
                        }
                    }
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.e("BookmarkDialog", t.toString())
                    }
                })
            }
        }
    }
}