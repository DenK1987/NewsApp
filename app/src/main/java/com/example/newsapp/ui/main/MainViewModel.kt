package com.example.newsapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.api.NewsRepository
import com.example.newsapp.models.NewsResponse
import com.example.newsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

//    private val _all = MutableLiveData<NewsResponse>()
//    val all: LiveData<NewsResponse>
//        get() = _all
//
//    init {
//        getAll()
//    }
//
//    private fun getAll() = viewModelScope.launch {
//        repository.getAll().let {
//            if (it.isSuccessful) {
//                _all.postValue(it.body())
//            } else {
//                Log.d("checkData", "Failed to load articles: ${it.errorBody()}")
//            }
//        }
//    }

    val newsLiveData: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var newsPage = 1

    init {
        getNews("ru")
    }

    private fun getNews(countryCode: String) {
        viewModelScope.launch {
            newsLiveData.postValue(Resource.Loading())
            val response = repository.getNews(countryCode = countryCode, pageNumber = newsPage)
            if (response.isSuccessful) {
                response.body().let { res ->
                    newsLiveData.postValue(Resource.Success(res))
                }
            } else {
                newsLiveData.postValue(Resource.Error(message = response.message()))
            }
        }
    }

}