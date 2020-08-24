package com.ftadev.baman.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ftadev.baman.databinding.ItemListBinding
import com.ftadev.baman.databinding.ItemProgressBinding
import com.ftadev.baman.repository.model.Item
import com.ftadev.baman.ui.activities.DetailActivity

class PaginationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ITEM = 0
    private val LOADING = 1

    private var mList = ArrayList<Item>()
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
                listVH.mName.text = item.name
                listVH.mDate.text = item.createDate.toString()
                listVH.mDesc.text = item.description

                val view = holder.itemView

                Glide.with(view)
                    .load(item.imageUrl)
                    .into(listVH.mPhoto)

                view.setOnClickListener {
                    val intent = Intent(view.context, DetailActivity::class.java)
                    intent.putExtra("id", item.id)
                    view.context.startActivity(intent)
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
        var mDesc = binding.desc
        var mDate = binding.date
        var mPhoto = binding.photo

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

    fun addAll(items: ArrayList<Item>) {
        mList.addAll(items)
        notifyDataSetChanged()
    }

    fun insert(position: Int, item: Item) {
        mList.add(position, item)
        notifyItemInserted(position)
    }

    fun remove(item: Item) {
        val position = mList.indexOf(item)
        mList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateItems(items: ArrayList<Item>) {
        mList = items
        notifyDataSetChanged()
    }

    fun clearList() {
        mList.clear()
        notifyDataSetChanged()
    }
}


