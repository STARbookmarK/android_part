package com.example.bookmarkkk

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookmarkkk.databinding.ListBookmarkItemBinding

class BookMarkAdapter(private val context : Context) : RecyclerView.Adapter<BookMarkAdapter.Holder>() {

    private val bookmarkList = ArrayList<Bookmark>()
    // list, grid view type 분리
    private lateinit var binding : ListBookmarkItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        binding = ListBookmarkItemBinding.inflate(LayoutInflater.from(context))
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.onBind(bookmarkList[position])
    }

    override fun getItemCount(): Int {
        return bookmarkList.size
    }

    fun addItem(item : Bookmark){
        bookmarkList.add(item)
    }

    fun deleteItem(position: Int){
        bookmarkList.removeAt(position)
    }

    fun removeAll(){
        bookmarkList.clear()
    }

    inner class Holder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun onBind(item: Bookmark){
            binding.bookmark = item
        }
    }
}