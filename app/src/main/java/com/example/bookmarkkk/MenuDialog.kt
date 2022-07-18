package com.example.bookmarkkk

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bookmarkkk.databinding.MenuDialogBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuDialog(id: Int) : DialogFragment(), View.OnClickListener {

    private val binding by viewBinding(MenuDialogBinding::bind)
    private val viewModel : ViewModel by viewModel()
    private val bookmarkId = id

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

//    fun deleteBookmark(id : Int){
//        NetworkClient.bookmarkService.deleteBookmark(BookmarkId(id))
//            .enqueue(object : Callback<Void> {
//                override fun onResponse(call: Call<Void>, response: Response<Void>) {
//                    if (response.isSuccessful){
//                        Toast.makeText(context, "삭제", Toast.LENGTH_SHORT).show()
//                    }else {
//                        Log.e(UserRepository.TAG, response.code().toString())
//                    }
//                }
//                override fun onFailure(call: Call<Void>, t: Throwable) {
//                    Log.e(UserRepository.TAG, t.toString())
//                }
//            })
//    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.deleteBookmarkBtn -> {
                viewModel.deleteBookmark(BookmarkId(bookmarkId))
                this.dismiss()
            }
        }
    }
}