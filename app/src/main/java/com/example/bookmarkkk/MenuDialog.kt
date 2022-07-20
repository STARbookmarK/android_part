package com.example.bookmarkkk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bookmarkkk.databinding.MenuDialogBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuDialog() : DialogFragment(), View.OnClickListener {

    private val binding by viewBinding(MenuDialogBinding::bind)
    private val viewModel : ViewModel by viewModel()
    var bookmarkId = id
    var tags = ""
    var description = ""
    var rate = 0

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
            R.id.deleteBookmarkBtn -> {
                viewModel.deleteBookmark(BookmarkId(bookmarkId))
                this.dismiss()
                val intent = Intent(context, MainActivity::class.java) // 메인화면으로 이동
                startActivity(intent)
            }
            R.id.modifyBookmarkBtn -> {
                val dialog = BookmarkModifyDialog().apply {
                    m_bookmarkId = bookmarkId
                    m_tags = tags
                    m_description = description
                    m_rate = rate
                }
                dialog.show(parentFragmentManager, "MenuDialog")
            }
        }
    }
}