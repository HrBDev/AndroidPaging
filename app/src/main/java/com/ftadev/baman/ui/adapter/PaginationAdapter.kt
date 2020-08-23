package com.ftadev.baman.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ftadev.baman.R
import com.ftadev.baman.model.Item

class PaginationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ITEM = 0
    private val LOADING = 1

    private var mList = ArrayList<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var viewHolder: RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            ITEM -> {
                val viewItem = inflater.inflate(R.layout.item_list, parent, false)
                viewHolder = ListViewHolder(viewItem)
            }
            LOADING -> {
                val viewLoading = inflater.inflate(R.layout.item_progress, parent, false)
                viewHolder = LoadingViewHolder(viewLoading)
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

                Glide.with(holder.itemView)
                    .load(item.imageUrl)
                    .into(listVH.mPhoto)
            }
            LOADING -> {
                val loadingVH = holder as LoadingViewHolder
                loadingVH.mProgressBar.visibility = View.VISIBLE
            }
        }
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var mName: TextView = itemView.findViewById(R.id.name)
        var mDesc: TextView = itemView.findViewById(R.id.desc)
        var mDate: TextView = itemView.findViewById(R.id.date)
        var mPhoto: ImageView = itemView.findViewById(R.id.photo)

        override fun onClick(p0: View?) {
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mProgressBar: ProgressBar = itemView.findViewById(R.id.loadmore_progress)
    }

    override fun getItemCount() = mList.size

    override fun getItemViewType(position: Int): Int = if (position == mList.size - 1) LOADING else ITEM

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


