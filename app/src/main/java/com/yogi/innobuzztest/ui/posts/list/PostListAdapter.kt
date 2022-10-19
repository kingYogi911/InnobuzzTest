package com.yogi.innobuzztest.ui.posts.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yogi.innobuzztest.R
import com.yogi.innobuzztest.data.posts.PostItem
import com.yogi.innobuzztest.databinding.PostItemRvBinding

class PostListAdapter(
    private val onClick: (PostItem) -> Unit
) : RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    private val dataset: MutableList<PostItem> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<PostItem>) {
        dataset.clear()
        dataset += data
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = PostItemRvBinding.bind(itemView)

        init {
            binding.root.setOnClickListener {
                onClick(dataset[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater
            .from(parent.context)
            .inflate(R.layout.post_item_rv, parent, false)
            .let { ViewHolder(it) }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tv.text = dataset[position].title
    }

    override fun getItemCount() = dataset.size
}