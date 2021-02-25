package com.example.mychecklist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mychecklist.databinding.CheclistRowLayoutBinding
import com.example.mychecklist.model.checklist.ChecklistItem

class ItemAdapter (private val onClickListener: OnClickListener) :
    ListAdapter<ChecklistItem, ItemAdapter.ItemViewHolder>(ItemDiffUtils) {

    companion object ItemDiffUtils : DiffUtil.ItemCallback<ChecklistItem>() {
        override fun areItemsTheSame(oldItem: ChecklistItem, newItem: ChecklistItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ChecklistItem, newItem: ChecklistItem): Boolean {
            return oldItem.checklistId == newItem.checklistId
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val view =
            CheclistRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val position = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(position)
        }
        holder.bind(position)
    }

    inner class ItemViewHolder(private val binding: CheclistRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemName: ChecklistItem?) {
            binding.apply {
                item = itemName
                executePendingBindings()
            }
        }
    }

    class OnClickListener(val clickListener: (checklistItem: ChecklistItem) -> Unit) {
        fun onClick(checklistItem: ChecklistItem) = clickListener(checklistItem)
    }
}