package com.siba.searchmvvmpractice.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siba.searchmvvmpractice.domain.DomainRepository
import com.siba.searchmvvmpractice.domain.DomainUsers
import com.siba.searchmvvmpractice.local.entity.RecentSearchTerm
import com.siba.searchmvvmpractice.remote.model.repository.UserRepositoryCatalog
import com.siba.searchmvvmpractice.repository.SearchRepository
import kotlinx.coroutines.launch

class SearchViewModel(
        private val repository: SearchRepository
) : ViewModel() {

    private val _keyword = MutableLiveData<String>()
    val keyword: MutableLiveData<String>
        get() = _keyword

    private val _githubRepo = MutableLiveData<UserRepositoryCatalog>()
    val githubRepo: MutableLiveData<UserRepositoryCatalog>
        get() = _githubRepo

    var allRecentSearchTerm: LiveData<List<RecentSearchTerm>> = repository.getAllSearchTerm()

    /*fun searchRepo() = viewModelScope.launch {
        try {
            _githubRepo.value = repository.fetchRepository(_keyword.value.toString())
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }*/

    fun insertRecentSearchTermToAppDatabase() = viewModelScope.launch {
        val recentSearchTerm = RecentSearchTerm(keyword = _keyword.value.toString())
        repository.insertRecentSearchTerm(recentSearchTerm)
    }

    fun insertGithubUserToAppDatabase() = viewModelScope.launch {
        repository.insertGithubUserToAppDatabase(_keyword.value.toString())
    }

    fun fetchGithubUserFromAppDatabase(keyword : String): LiveData<List<DomainUsers>> {
        return repository.fetchDatabaseGithubUser(keyword)
    }

    fun insertGithubRepositoryToAppDatabase() = viewModelScope.launch {
        repository.insertGithubRepositoryToAppDatabase(_keyword.value.toString())
    }

    fun fetchGithubRepositoryFromAppDatabase(keyword : String) : LiveData<List<DomainRepository>>{
        return repository.fetchDatabaseGithubRepository(keyword)
    }
}