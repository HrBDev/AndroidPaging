package com.ftadev.androidpaging.ui.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.ftadev.androidpaging.databinding.ActivityMainBinding
import com.ftadev.androidpaging.ui.PaginationScrollListener
import com.ftadev.androidpaging.ui.adapter.PaginationAdapter
import com.ftadev.androidpaging.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private lateinit var adapter: PaginationAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    private val TOTAL_PAGES = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        viewModel.photos.observe(this, { result ->
            adapter.addAll(result)
            if (viewModel.currentPage == TOTAL_PAGES) {
                viewModel.isLastPage = true
                adapter.disableLoading()
            }
            binding.progress.visibility = View.GONE
            viewModel.isLoading = false
        })

    }

    private fun setupRecyclerView() {
        adapter = PaginationAdapter(viewModel.recyclerList)

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rv.layoutManager = linearLayoutManager
        binding.rv.itemAnimator = DefaultItemAnimator()

        binding.rv.adapter = adapter

        binding.rv.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {
                viewModel.currentPage += 1
                viewModel.loadPics()
            }

            override val totalPageCount: Int = TOTAL_PAGES
            override val isLastPage: Boolean = viewModel.isLastPage
            override val isLoading: Boolean = viewModel.isLoading
        })
    }
}