package com.frank.mindvalley.ui.channels.adapters

import androidx.recyclerview.widget.DiffUtil
import com.frank.mindvalley.models.CourseModel

class CourseDiff : DiffUtil.ItemCallback<CourseModel>() {

    override fun areItemsTheSame(oldItem: CourseModel, newItem: CourseModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CourseModel, newItem: CourseModel): Boolean {
        return oldItem.title == newItem.title
    }
}