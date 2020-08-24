package com.ftadev.baman.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ftadev.baman.R
import com.ftadev.baman.repository.remote.APIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private val mApiService by lazy { APIService.instance }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        intent.getStringExtra("id")?.run {
            loadData(this)
        }

    }

    @SuppressLint("CheckResult")
    fun loadData(id: String) {
        mApiService.getSampleItem(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                detail_name.text = result.data.name
                detail_date.text = result.data.createDate.toString()
                detail_desc.text = result.data.description
                detail_url.text = result.data.shareUrl
                Glide.with(this).load(result.data.imageUrl).into(detail_photo)
            }, {
                it.printStackTrace()
            })
    }
}