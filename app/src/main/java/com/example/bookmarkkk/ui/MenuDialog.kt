package com.example.bookmarkkk.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bookmarkkk.R
import com.example.bookmarkkk.api.model.BookmarkForModify
import com.example.bookmarkkk.api.model.BookmarkId
import com.example.bookmarkkk.databinding.MenuDialogBinding
import com.example.bookmarkkk.viewModel.ViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuDialog(
    private val data: BookmarkForModify
    ) : DialogFragment(), OnClickListener {

    private val binding by viewBinding(MenuDialogBinding::bind)
    private val viewModel : ViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.menu_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.deleteBookmarkBtn.setOnClickListener(this)
        binding.modifyBookmarkBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.deleteBookmarkBtn -> { // 삭제
                //viewModel.deleteBookmark(BookmarkId(data.id))
                lifecycleScope.launch {
                    viewModel.deleteBookmark(BookmarkId(data.id))
                }
                this.dismiss()
                val intent = Intent(context, MainActivity::class.java) // 메인화면으로 이동
                startActivity(intent)
            }
            R.id.modifyBookmarkBtn -> { // 내용 수정
                val dialog = BookmarkModifyDialog(data)
                dialog.show(parentFragmentManager, "MenuDialog")
            }
        }
    }
}