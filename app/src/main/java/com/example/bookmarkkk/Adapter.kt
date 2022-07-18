package com.example.bookmarkkk

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bookmarkkk.databinding.ListBookmarkItemBinding

class BookMarkAdapter(private val context : Context) : RecyclerView.Adapter<BookMarkAdapter.Holder>() {

    private val bookmarkList = ArrayList<Bookmark>()
    private lateinit var listBinding : ListBookmarkItemBinding

    interface ItemClick{
        fun onClick(v: View, pos: Int, list: ArrayList<Bookmark>)
    }
    var itemClick : ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(context)
        listBinding = ListBookmarkItemBinding.inflate(inflater, parent, false)
        return Holder(listBinding.root)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.onBind(bookmarkList[position])
        if (itemClick!=null){
            holder.view.setOnClickListener { v ->
                itemClick?.onClick(v, position, bookmarkList)
            }
        }
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

    inner class Holder(val view: View) : RecyclerView.ViewHolder(view) {

        fun onBind(item: Bookmark){
            listBinding.bookmark = item
            listBinding.rate.text = item.rate.toString()
            listBinding.deleteBtn.setOnClickListener {
                Log.e("Adapter", item.id.toString())
                val dialog = MenuDialog(item.id)
                (context as AppCompatActivity).let {
                    dialog.show(it.supportFragmentManager, "MenuDialog")
                }
            }
        }
    }
}