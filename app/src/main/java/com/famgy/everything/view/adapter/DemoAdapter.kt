package com.famgy.everything.view.adapter

import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.famgy.everything.databinding.ListItemDemoBinding
import com.famgy.everything.model.bean.Demo


class DemoAdapter(
    private val onClickListener: (demo: Demo) -> Unit,
    private val onLongClickListener: (holder: RecyclerView.ViewHolder) -> Unit
) : ListAdapter<Demo, DemoViewHolder>(Demo.DIFF_CALLBACK) {

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DemoViewHolder {
        val binding = ListItemDemoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DemoViewHolder(binding, onClickListener, onLongClickListener)
    }

    override fun onBindViewHolder(holder: DemoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DemoViewHolder(
    private val binding: ListItemDemoBinding,
    private val onClickListener: (demo: Demo) -> Unit,
    private val onLongClickListener: (holder: RecyclerView.ViewHolder) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Demo) {
        binding.apply {
            demo = item

            executePendingBindings()
        }

        val cardView = binding.cardView
        cardView.setOnClickListener {
            onClickListener(item)
        }

        cardView.setOnLongClickListener {
            onLongClickListener(this)
            true
        }
    }
}
