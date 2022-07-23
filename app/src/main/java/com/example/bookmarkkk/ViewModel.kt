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
    //var bookmarkList : MutableLiveData<List<Bookmark>> = MutableLiveData()
    var bookmarkList : MutableLiveData<List<Bookmark>> = MutableLiveData()
    var tagList : MutableLiveData<List<HashTag>> = MutableLiveData()
    var urlList : MutableLiveData<List<String>> = MutableLiveData()

    init {
        repository.getUser()
        viewModelScope.launch {
            repository.getBookmarks()
            repository.getImageUrl()
        }
        repository.getHashTags()
        userData = repository.userData
        bookmarkList = repository.bookmarkList
        tagList = repository.tagList
        urlList = repository.urlList
    }

    fun addBookmark(item: BookmarkForAdd){
        viewModelScope.launch {
            repository.addBookmark(item)
        }
    }

    fun modifyBookmark(item: BookmarkForModify) {
        viewModelScope.launch {
            repository.modifyBookmark(item)
        }
    }

    fun deleteBookmark(id: BookmarkId) {
        viewModelScope.launch {
            repository.deleteBookmark(id)
        }
    }

//    fun changeBio(info: String){
//        repository.changeBio(info)
//    }

    fun changeBio(info: String) {
        viewModelScope.launch {
            repository.changeBio(info)
        }
    }

}