package com.ftadev.baman.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.ftadev.baman.R
import com.ftadev.baman.model.Page
import com.ftadev.baman.repository.remote.APIService
import com.ftadev.baman.ui.PaginationScrollListener
import com.ftadev.baman.ui.adapter.PaginationAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val mApiService by lazy { APIService.instance }

    lateinit var adapter: PaginationAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    private val PAGE_START = 1
    private val TOTAL_PAGES = 6
    private var currentPage = PAGE_START

    private var isLoadingVar = false
    private var isLastPageVar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()

        loadFirstPage()
    }

    private fun setupRecyclerView() {
        adapter = PaginationAdapter()

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv.layoutManager = linearLayoutManager
        rv.itemAnimator = DefaultItemAnimator()

        rv.adapter = adapter

        rv.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {
                isLoadingVar = true
                currentPage += 1
                loadNextPage()
            }

            override val totalPageCount: Int = TOTAL_PAGES
            override val isLastPage: Boolean = isLastPageVar
            override val isLoading: Boolean = isLoadingVar
        })
    }

    @SuppressLint("CheckResult")
    private fun loadFirstPage() {
        progress.visibility = View.VISIBLE
        currentPage = PAGE_START
        mApiService.getSampleList(Page(page = currentPage.toString()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                progress.visibility = View.GONE
                adapter.addAll(result.data.list)
                if (currentPage > TOTAL_PAGES) {
                    isLastPageVar = true
                    adapter.removeLoading()
                }
            }, {
                it.printStackTrace()
            })
    }

    @SuppressLint("CheckResult")
    private fun loadNextPage() {
        mApiService.getSampleList(Page(page = currentPage.toString()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                isLoadingVar = false
                adapter.addAll(result.data.list)
                if (currentPage == TOTAL_PAGES) {
                    isLastPageVar = true
                    adapter.removeLoading()
                }
            }, {
                it.printStackTrace()
            })
    }

}