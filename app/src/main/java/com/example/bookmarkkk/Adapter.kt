package com.example.bookmarkkk

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookmarkkk.databinding.ListBookmarkItemBinding
import com.haroldadmin.opengraphKt.getOpenGraphTags
import kotlinx.coroutines.*
import kotlinx.coroutines.selects.whileSelect
import retrofit2.http.Url
import java.net.URL
import java.net.URLEncoder

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
//        CoroutineScope(Dispatchers.IO).launch {
//            // val url = URL(urlList[position])
//            withContext(Dispatchers.Main){
//                val tagUrl = URL(bookmarkList[position].address).getOpenGraphTags().image.toString()
//                Glide.with(context).load(tagUrl).into(holder.imageView)
//            }
//        }
        if (itemClick!=null){
            holder.view.setOnClickListener { v ->
                itemClick?.onClick(v, position, bookmarkList)
            }
        }
    }

    override fun getItemCount(): Int {
        return bookmarkList.size
    }

    fun add(item : List<Bookmark>){
        bookmarkList.addAll(item)
    }

    fun deleteItem(position: Int){
        bookmarkList.removeAt(position)
    }

    fun removeAll(){
        bookmarkList.clear()
    }

    inner class Holder(val view: View) : RecyclerView.ViewHolder(view) {
        //val imageView: ImageView = view.findViewById(R.id.bookmarkImgView)
        fun onBind(item: Bookmark){
            listBinding.bookmark = item
            listBinding.rate.text = item.rate.toString()
            listBinding.deleteBtn.setOnClickListener { // 버튼 클릭 시 다이얼로그 오픈
                val dialog = MenuDialog().apply {
                    bookmarkId = item.id
                    tags = item.tags
                    description = item.description
                    rate = item.rate
                }
                (context as AppCompatActivity).let {
                    dialog.show(it.supportFragmentManager, "MenuDialog")
                }
            }
            Glide.with(context).load(item.image).into(listBinding.bookmarkImgView)
        }
    }
}