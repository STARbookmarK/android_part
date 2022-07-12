package com.example.bookmarkkk

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bookmarkkk.databinding.GridBookmarkItemBinding
import com.example.bookmarkkk.databinding.ListBookmarkItemBinding

class BookMarkAdapter(private val context : Context) : RecyclerView.Adapter<BookMarkAdapter.Holder>() {

    private val bookmarkList = ArrayList<Bookmark>()
    // list, grid view type 분리
    private lateinit var listBinding : ListBookmarkItemBinding
    private lateinit var gridBinding : GridBookmarkItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(context)
        listBinding = ListBookmarkItemBinding.inflate(inflater, parent, false)
        return Holder(listBinding.root)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        //
        holder.onBind(bookmarkList[position])
    }

    override fun getItemCount(): Int {
        return bookmarkList.size
    }

    fun add (item : List<Bookmark>){
        bookmarkList.addAll(item)
    }

    fun deleteItem(position: Int){
        bookmarkList.removeAt(position)
    }

    fun removeAll(){
        bookmarkList.clear()
    }

    inner class Holder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun onBind(item: Bookmark){
            listBinding.bookmark = item
            listBinding.rate.text = item.rate.toString()
        }
    }

//    inner class GridHolder(private val view: View) : RecyclerView.ViewHolder(view) {
//        fun onBind(item: Bookmark){
//            gridBinding.bookmark = item
//        }
//    }

    companion object{
        const val LIST_TYPE = 1
        const val GRID_TYPE = 0
    }
}