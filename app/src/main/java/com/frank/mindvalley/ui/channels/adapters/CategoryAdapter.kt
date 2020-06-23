package com.frank.mindvalley.ui.channels.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.frank.mindvalley.databinding.AdapterCategoryBinding
import com.frank.mindvalley.models.CategoryModel

class CategoryAdapter :
    ListAdapter<CategoryModel, CategoryAdapter.CategoryViewHolder>(CategoryDiff()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }

    class CategoryViewHolder private constructor(val dataBinding: AdapterCategoryBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {

        companion object {
            fun from(parent: ViewGroup): CategoryViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val dataBinding = AdapterCategoryBinding.inflate(inflater, parent, false)
                return CategoryViewHolder(dataBinding)
            }
        }

        fun bind(category: CategoryModel) {
            dataBinding.categoryModel = category
            dataBinding.executePendingBindings()
        }
    }

    class CategoryDiff : DiffUtil.ItemCallback<CategoryModel>() {
        override fun areItemsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
            return oldItem.name == newItem.name
        }
    }

}