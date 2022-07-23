package com.example.bookmarkkk

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookmarkkk.databinding.ListBookmarkItemBinding
import com.google.android.material.transition.Hold
import com.haroldadmin.opengraphKt.getOpenGraphTags
import kotlinx.coroutines.*
import java.net.URL

class BookMarkAdapter(private val context : Context)
    : RecyclerView.Adapter<BookMarkAdapter.Holder>() {

    companion object{
        const val IMAGE_TYPE = 0
        const val BOOKMARK_TYPE = 1
    }

    private val bookmarkList = ArrayList<Bookmark>() // 북마크 리스트
    private val urlList = ArrayList<String>() // 북마크 주소 리스트
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
        try {
            holder.onBindTest(urlList[position])
        }catch (e : IndexOutOfBoundsException) {
            Log.e("Adapter", e.toString())
        }
        try {
            holder.onBind(bookmarkList[position])
            if (itemClick!=null){
                holder.view.setOnClickListener { v ->
                    itemClick?.onClick(v, position, bookmarkList)
                }
            }
        }catch (e: IndexOutOfBoundsException) {
            Log.e("Adapter", e.toString())
        }
//        holder.onBindTest(urlList[position])
//        holder.onBind(bookmarkList[position])
//        if (itemClick!=null){
//            holder.view.setOnClickListener { v ->
//                itemClick?.onClick(v, position, bookmarkList)
//            }
//        }
    }

    override fun getItemCount(): Int {
        //return bookmarkList.size // 리스트 두개일때 어떤 리스트의 사이즈를 리턴하는가?
        return urlList.size
    }

    fun add(item : List<Bookmark>){
        bookmarkList.addAll(item)
    }

    fun addUrl(url: List<String>){
        urlList.addAll(url)
    }

    fun removeAll(){
        bookmarkList.clear()
    }

    fun removeAllUrl(){
        urlList.clear()
    }

    inner class Holder(val view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.bookmarkImgView)
        fun onBindTest(url: String) {
            Log.e("onBindTest in Holder", url)
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    val tagUrl = URL(url).getOpenGraphTags().image.toString()
                    Log.e("onBindTest in Holder scope", tagUrl)
                    Glide.with(context).load(tagUrl).into(imageView)
                }
            }
        }
        fun onBind(item: Bookmark) {
            listBinding.bookmark = item
            listBinding.rate.text = item.rate.toString()
            // String 해시태그 쪼개서 리스트형으로 만들기
            val tag = mutableListOf("web", "naver") // 임시 해시태그
            listBinding.deleteBtn.setOnClickListener { // 버튼 클릭 시 다이얼로그 오픈
                val data = BookmarkForModify(
                    item.id, item.title, item.address, item.description, item.rate,true, tag
                )
                val dialog = MenuDialog(data)
                (context as AppCompatActivity).let {
                    dialog.show(it.supportFragmentManager, "MenuDialog")
                }
            }
        }
    }
}