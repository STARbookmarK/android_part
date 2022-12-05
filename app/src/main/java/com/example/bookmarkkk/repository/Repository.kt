package com.example.bookmarkkk.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.bookmarkkk.*
import com.example.bookmarkkk.api.model.BioOfUserInfo
import com.example.bookmarkkk.api.model.Bookmark
import com.example.bookmarkkk.api.model.BookmarkForAdd
import com.example.bookmarkkk.api.model.BookmarkForModify
import com.example.bookmarkkk.api.model.BookmarkId
import com.example.bookmarkkk.api.model.HashTag
import com.example.bookmarkkk.api.model.UserInfo
import com.example.bookmarkkk.api.request.BookmarkService
import com.example.bookmarkkk.api.request.UserInfoService
import io.github.muddz.styleabletoast.StyleableToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class UserRepository(
    private val context: Context,
    private val user: UserInfoService,
    private val bookmark: BookmarkService
) {

    val userData : MutableLiveData<UserInfo> = MutableLiveData()
    val tagList : MutableLiveData<List<HashTag>> = MutableLiveData()
    val urlList : MutableLiveData<List<String>> = MutableLiveData()
    val bookmarkList : MutableLiveData<List<Bookmark>> = MutableLiveData()

    suspend fun getUser() {
        val result = user.getUserInfo()
        if (result.isSuccess){
            val body = result.getOrNull()
            body?.let {
                userData.value = it
            }
        }else {
            Log.e(TAG, result.toString())
        }
    }

    suspend fun changeBio(info: String) {
        try {
            user.changeBio(BioOfUserInfo(info))
            StyleableToast.makeText(context, "소개글 변경", R.style.bioToast).show()
        }catch (e: Exception) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    suspend fun getBookmarks() {
        try {
            val response = bookmark.getAllBookmarks()
            val body = response.body()
            if (body != null)
                bookmarkList.value = body
        }catch (e: Exception) {
            Log.e(TAG, "get bookmarks error")
        }
    }

    suspend fun getImageUrl() {
        try {
            val response = bookmark.getAllBookmarks()
            val body = response.body()
            if (body != null){
                val list = mutableListOf<String>()
                body.forEach { bookmark ->
                    list.add(bookmark.address)
                }
                urlList.value = list
            }
        }catch (e: Exception){
            Log.e(TAG, "get imageUrl error")
        }
    }

    fun getHashTags(){
        bookmark.getTags()
            .enqueue(object : Callback<List<HashTag>> {
                override fun onResponse(call: Call<List<HashTag>>, response: Response<List<HashTag>>) {
                    if (response.isSuccessful){
                        response.body()?.let {
                            tagList.value = it
                        }
                    }else{
                        Log.e("repo - getTag", response.toString())
                    }
                }
                override fun onFailure(call: Call<List<HashTag>>, t: Throwable) {
                    Log.e("repo - getTag", t.toString())
                }
            })
    }

    suspend fun addBookmark(item : BookmarkForAdd) {
        val result = bookmark.addBookmark(item)
        if (result.isSuccess){
            Toast.makeText(context, "북마크 추가", Toast.LENGTH_SHORT).show()
        }else {
            Log.e(TAG, result.toString())
        }
    }

    suspend fun deleteBookmark(id : BookmarkId) {
        val result = bookmark.deleteBookmark(id)
        if (result.isSuccess){
            Toast.makeText(context, "북마크 삭제", Toast.LENGTH_SHORT).show()
        }else {
            Log.e(TAG, result.toString())
        }
    }

    suspend fun modifyBookmark(item: BookmarkForModify) {
        val result = bookmark.updateBookmark(item)
        if (result.isSuccess){
            Toast.makeText(context, "북마크 수정", Toast.LENGTH_SHORT).show()
        }else {
            Log.e(TAG, result.toString())
        }
    }


//// 수정 /////
//    suspend fun getUser() {
//        val result = NetworkClient.userInfoService.getUserInfo()
//        if (result.isSuccess){
//            val body = result.getOrNull()
//            body?.let {
//                userData.value = it
//            }
//        }else {
//            Log.e(TAG, result.toString())
//        }
//    }

    // body가 필요한 경우 vs body가 필요없는 경우
//    suspend fun changeBio(info: String) {
//        try {
//            NetworkClient.userInfoService.changeBio(BioOfUserInfo(info))
//            StyleableToast.makeText(context, "소개글 변경", R.style.bioToast).show()
//        }catch (e : Exception){
//            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
//        }
//    }

//    suspend fun getBookmarks() {
//        try {
//            val response = NetworkClient.bookmarkService.getAllBookmarks()
//            val body = response.body()
//            if (body != null)
//                bookmarkList.value = body
//        }catch (e : Exception){
//            Log.e(TAG, "get bookmarks error")
//        }
//    }

//    suspend fun getImageUrl() {
//        try {
//            val response = NetworkClient.bookmarkService.getAllBookmarks()
//            val body = response.body()
//            if (body != null){
//                val list = mutableListOf<String>()
//                body.forEach { bookmark ->
//                    list.add(bookmark.address)
//                }
//                urlList.value = list
//            }
//        }catch (e: Exception){
//            Log.e(TAG, "get imageUrl error")
//        }
//    }

//fun getHashTags(){
//    NetworkClient.bookmarkService.getTags()
//        .enqueue(object : Callback<List<HashTag>>{
//            override fun onResponse(call: Call<List<HashTag>>, response: Response<List<HashTag>>) {
//                if (response.isSuccessful){
//                    response.body()?.let {
//                        tagList.value = it
//                    }
//                }else{
//                    Log.e("repo - getTag", response.toString())
//                }
//            }
//            override fun onFailure(call: Call<List<HashTag>>, t: Throwable) {
//                Log.e("repo - getTag", t.toString())
//            }
//        })
//}

//    suspend fun addBookmark(item : BookmarkForAdd) {
//        val result = NetworkClient.bookmarkService.addBookmark(item)
//        if (result.isSuccess){
//            Toast.makeText(context, "북마크 추가", Toast.LENGTH_SHORT).show()
//        }else {
//            Log.e(TAG, result.toString())
//        }
//    }

//    suspend fun deleteBookmark(id : BookmarkId) {
//        val result = NetworkClient.bookmarkService.deleteBookmark(id)
//        if (result.isSuccess){
//            Toast.makeText(context, "북마크 삭제", Toast.LENGTH_SHORT).show()
//        }else {
//            Log.e(TAG, result.toString())
//        }
//    }
//
//    suspend fun modifyBookmark(item: BookmarkForModify) {
//        val result = NetworkClient.bookmarkService.updateBookmark(item)
//        if (result.isSuccess){
//            Toast.makeText(context, "북마크 수정", Toast.LENGTH_SHORT).show()
//        }else {
//            Log.e(TAG, result.toString())
//        }
//    }

    ////// 수정 //////

    // Retrofit result vs Call<>
//    fun getUser(){ // 회원정보 조회
//        NetworkClient.userInfoService.getUserInfo()
//            .enqueue(object: Callback<UserInfo> {
//                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>){
//                    if (response.isSuccessful){
//                        response.body()?.let {
//                            userData.value = it
//                        }
//                    }else {
//                        Log.e(TAG, response.toString())
//                    }
//                }
//                override fun onFailure(call: Call<UserInfo>, t: Throwable){
//                    Log.e(TAG, t.toString())
//                }
//            })
//    }

//    suspend fun getUser() {
//        try {
//            val response = NetworkClient.userInfoService.getUserInfo()
//            val body = response.body()
//            if (body != null){
//                userData.value = body
//            }
//        }catch (e: Exception) {
//            Log.e(TAG, e.toString())
//        }
//    }

    // return null

    companion object{
        const val TAG = "REPOSITORY"
    }
}