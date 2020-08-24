package com.ftadev.baman.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ftadev.baman.databinding.ActivityDetailBinding
import com.ftadev.baman.repository.remote.APIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailActivity : AppCompatActivity() {
    private val mApiService by lazy { APIService.instance }
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                binding.detailName.text = result.data.name
                binding.detailDate.text = result.data.createDate.toString()
                binding.detailDesc.text = result.data.description
                binding.detailUrl.text = result.data.shareUrl
                Glide.with(this).load(result.data.imageUrl).into(binding.detailPhoto)
            }, {
                it.printStackTrace()
            })
    }
}