package com.example.bookmarkkk.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookmarkkk.*
import com.example.bookmarkkk.api.model.Bookmark
import com.example.bookmarkkk.api.model.BookmarkForAdd
import com.example.bookmarkkk.api.model.BookmarkForModify
import com.example.bookmarkkk.api.model.BookmarkId
import com.example.bookmarkkk.api.model.HashTag
import com.example.bookmarkkk.api.model.UserInfo
import com.example.bookmarkkk.repository.UserRepository
import kotlinx.coroutines.launch

class ViewModel(private val repository: UserRepository) : ViewModel() {

    var userData : MutableLiveData<UserInfo> = MutableLiveData()
    var bookmarkList : MutableLiveData<List<Bookmark>> = MutableLiveData()
    var tagList : MutableLiveData<List<HashTag>> = MutableLiveData()
    var urlList : MutableLiveData<List<String>> = MutableLiveData()

    init {
        viewModelScope.launch {
            repository.getUser()
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

    fun changeBio(info: String) {
        viewModelScope.launch {
            repository.changeBio(info)
        }
    }
}