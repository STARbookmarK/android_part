package com.example.bookmarkkk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnKeyListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bookmarkkk.databinding.AddBookmarkBinding
import com.google.android.material.chip.Chip
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddBookmarkDialog : DialogFragment(), OnClickListener{

    private val binding by viewBinding(AddBookmarkBinding::bind)
    private val viewModel : ViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.add_bookmark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addRateBtn.setOnClickListener(this)
        binding.minusRateBtn.setOnClickListener(this)
        binding.bookmarkSaveBtn.setOnClickListener(this)

        // 태그 추가
        binding.tagEdit.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER){
                binding.tagGroup.addView(
                    createTagChip(
                        requireContext(),
                        binding.tagEdit.text.toString()
                    )
                )
                true
            }else {
                false
            }
        }
    }

    companion object{
        const val TAG = "AddBookmarkDialog"
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
                // 태그는 리스트화 !
                val list = binding.tagGroup.children.map {
                    (it as Chip).text.toString()
                }.toList()
                val item = BookmarkForAdd(
                    binding.titleEdit.text.toString(),
                    binding.urlEdit.text.toString(),
                    binding.descriptionEdit.text.toString(),
                    binding.rateText.text.toString().toInt(),
                    binding.sharedBtn.isChecked,
                    list
                )
                viewModel.addBookmark(item)
                this.dismiss()
                val intent = Intent(context, MainActivity::class.java) // 메인화면으로 이동
                startActivity(intent)
            }
        }
    }

}