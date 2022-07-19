package com.example.bookmarkkk

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.math.log

class ViewModel(private val repository: UserRepository) : ViewModel() {

    var userData : MutableLiveData<UserInfo> = MutableLiveData()
    var bookmarkList : MutableLiveData<List<Bookmark>> = MutableLiveData()
    var tagList : MutableLiveData<List<HashTag>> = MutableLiveData()

    init {
        repository.getUser()
        repository.getBookmarks()
        repository.getHashTags()
        userData = repository.userData
        bookmarkList = repository.bookmarkList
        tagList = repository.tagList
    }

    fun addBookmark(item: BookmarkForAdd){
        repository.addBookmark(item)
    }

    fun deleteBookmark(id : BookmarkId){
        repository.deleteBookmark(id)
    }

    fun changeBio(info: String){
        repository.changeBio(info)
    }

}