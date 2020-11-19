package com.siba.searchmvvmpractice.ui.viewmodel

import android.util.Log
import android.view.animation.Transformation
import androidx.lifecycle.*
import com.siba.searchmvvmpractice.local.entity.RecentSearchTerm
import com.siba.searchmvvmpractice.model.GithubUserData
import com.siba.searchmvvmpractice.model.ReposData
import com.siba.searchmvvmpractice.model.RetrofitData
import com.siba.searchmvvmpractice.model.SearchTermData
import com.siba.searchmvvmpractice.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(
        private val repository: SearchRepository
) : ViewModel() {

    private val _userName = MutableLiveData<String>()
    val userName: MutableLiveData<String>
        get() = _userName

    private val _githubUser = MutableLiveData<RetrofitData>()
    val githubUser: MutableLiveData<RetrofitData>
        get() = _githubUser

    private val _githubRepo = MutableLiveData<ReposData>()
    val githubRepo: MutableLiveData<ReposData>
        get() = _githubRepo

    var allSearch : LiveData<List<RecentSearchTerm>> = repository.getAll()

    fun searchUser() = viewModelScope.launch {
        githubUser.value = repository.fetchUser(userName.value.toString())
    }

    fun searchRepo() = viewModelScope.launch {
        githubRepo.value = repository.fetchRepo(userName.value.toString())
    }

    fun saveSearchTerm() = viewModelScope.launch(Dispatchers.IO) {
        val recentSearchTerm = RecentSearchTerm(keyword = userName.value.toString())
        repository.insert(recentSearchTerm)
    }

}