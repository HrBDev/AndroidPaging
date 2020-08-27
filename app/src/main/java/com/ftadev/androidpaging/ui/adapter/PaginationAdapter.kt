package com.ftadev.androidpaging.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ftadev.androidpaging.databinding.ItemListBinding
import com.ftadev.androidpaging.databinding.ItemProgressBinding
import com.ftadev.androidpaging.decreaseSize
import com.ftadev.androidpaging.repository.model.PhotoItem
import com.ftadev.androidpaging.repository.model.PhotoList

class PaginationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ITEM = 0
    private val LOADING = 1

    private var mList = PhotoList()
    private var isLoading = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var viewHolder: RecyclerView.ViewHolder

        when (viewType) {
            ITEM -> {
                val binding = ItemListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                viewHolder = ListViewHolder(binding)
            }
            LOADING -> {
                val binding = ItemProgressBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                viewHolder = LoadingViewHolder(binding)
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = mList[position]

        when (getItemViewType(position)) {
            ITEM -> {
                val listVH: ListViewHolder = holder as ListViewHolder
                listVH.mName.text = item.author

                val view = holder.itemView

                Glide.with(view)
                    .load(decreaseSize(item.download_url))
                    .into(listVH.mPhoto)

                view.setOnClickListener {
//                    val intent = Intent(view.context, DetailActivity::class.java)
//                    intent.putExtra("id", item.id)
//                    view.context.startActivity(intent)
                }
            }
            LOADING -> {
                val loadingVH = holder as LoadingViewHolder
                loadingVH.mProgressBar.visibility = View.VISIBLE
            }
        }
    }

    class ListViewHolder(binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        var mName = binding.name
        var mPhoto = binding.image

        override fun onClick(p0: View?) {
        }
    }

    class LoadingViewHolder(binding: ItemProgressBinding) : RecyclerView.ViewHolder(binding.root) {
        val mProgressBar = binding.loadmoreProgress
    }

    override fun getItemCount() = mList.size

    override fun getItemViewType(position: Int): Int =
        if (position == mList.size - 1 && isLoading) LOADING else ITEM

    fun addLoading() {
        isLoading = true
    }

    fun removeLoading() {
        isLoading = false
    }

    fun addAll(items: PhotoList) {
        mList.addAll(items)
        notifyDataSetChanged()
    }

    fun insert(position: Int, item: PhotoItem) {
        mList.add(position, item)
        notifyItemInserted(position)
    }

    fun remove(item: PhotoItem) {
        val position = mList.indexOf(item)
        mList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateItems(items: PhotoList) {
        mList = items
        notifyDataSetChanged()
    }

    fun clearList() {
        mList.clear()
        notifyDataSetChanged()
    }
}


