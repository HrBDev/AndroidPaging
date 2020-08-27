package com.ftadev.androidpaging.ui.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ftadev.androidpaging.repository.model.PhotoList
import com.ftadev.androidpaging.repository.remote.APIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {
    private val apiService by lazy { APIService.instance }

    var photoListData: MutableLiveData<PhotoList> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun getList(page: Int): MutableLiveData<PhotoList> {
        apiService.getPhotoList(page = page, limit = 10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                photoListData.value = result
            }, {
                it.printStackTrace()
            })
        return photoListData
    }
}