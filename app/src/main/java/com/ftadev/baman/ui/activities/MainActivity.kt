package com.ftadev.baman.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.ftadev.baman.databinding.ActivityMainBinding
import com.ftadev.baman.ui.PaginationScrollListener
import com.ftadev.baman.ui.adapter.PaginationAdapter
import com.ftadev.baman.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    lateinit var adapter: PaginationAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    private val PAGE_START = 1
    private val TOTAL_PAGES = 6
    private var currentPage = PAGE_START

    private var isLoadingVar = false
    private var isLastPageVar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setupRecyclerView()

        loadFirstPage()
    }

    private fun setupRecyclerView() {
        adapter = PaginationAdapter()

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rv.layoutManager = linearLayoutManager
        binding.rv.itemAnimator = DefaultItemAnimator()

        binding.rv.adapter = adapter

        binding.rv.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
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
        binding.progress.visibility = View.VISIBLE
        currentPage = PAGE_START
        viewModel.getList(page = currentPage.toString()).observe(this, Observer { result ->
            binding.progress.visibility = View.GONE
            adapter.addAll(result.data.list)
            if (currentPage > TOTAL_PAGES) {
                isLastPageVar = true
                adapter.removeLoading()
            }
        })
    }

    @SuppressLint("CheckResult")
    private fun loadNextPage() {
        viewModel.getList(page = currentPage.toString()).observe(this, Observer { result ->
            isLoadingVar = false
            adapter.addAll(result.data.list)
            if (currentPage == TOTAL_PAGES) {
                isLastPageVar = true
                adapter.removeLoading()
            }
        })
    }
}