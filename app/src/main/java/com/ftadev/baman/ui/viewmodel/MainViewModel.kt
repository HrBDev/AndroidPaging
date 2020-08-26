package com.ftadev.baman.ui.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ftadev.baman.repository.model.Page
import com.ftadev.baman.repository.model.SampleList
import com.ftadev.baman.repository.remote.APIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {
    private val apiService by lazy { APIService.instance }

    var sampleListData: MutableLiveData<SampleList> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun getList(page: String): MutableLiveData<SampleList> {
        apiService.getSampleList(Page(page = page))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                sampleListData.value = result
            }, {
                it.printStackTrace()
            })
        return sampleListData
    }
}