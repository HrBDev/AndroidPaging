package com.ftadev.androidpaging.ui.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ftadev.androidpaging.repository.model.PhotoList
import com.ftadev.androidpaging.repository.remote.APIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {
    var currentPage = 1
    var isLoading = false
    var isLastPage = false

    private val apiService by lazy { APIService.instance }

    val photos by lazy {
        // initial loading request
        MutableLiveData<PhotoList>().also {
            loadPics()
        }
    }

    @SuppressLint("CheckResult")
    fun loadPics() {
        isLoading = true
        if (currentPage <= 6) {
            apiService.getPhotoList(page = currentPage, limit = 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    photos.value = result
                }, {
                    it.printStackTrace()
                })
        }
    }
}