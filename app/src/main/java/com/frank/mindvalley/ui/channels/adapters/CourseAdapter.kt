package com.frank.mindvalley.ui.channels.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.frank.mindvalley.databinding.AdapterNewEpisodesBinding
import com.frank.mindvalley.models.CourseModel
import com.frank.mindvalley.utils.Screen

class CourseAdapter :
    ListAdapter<CourseModel, CourseAdapter.NewEpisodesViewHolder>(CourseDiff()) {

    var isForSeries: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewEpisodesViewHolder {
        return NewEpisodesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NewEpisodesViewHolder, position: Int) {
        val courseModel = getItem(position)
        holder.bind(courseModel, isForSeries)
    }

    class NewEpisodesViewHolder private constructor(val dataBinding: AdapterNewEpisodesBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {

        companion object {
            fun from(parent: ViewGroup): NewEpisodesViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val dataBinding = AdapterNewEpisodesBinding.inflate(inflater, parent, false)
                return NewEpisodesViewHolder(dataBinding)
            }
        }

        fun bind(courseModel: CourseModel, isForSeries: Boolean) {
            updateSizeOfView(isForSeries)
            dataBinding.courseModel = courseModel
            dataBinding.executePendingBindings()

        }

        private fun updateSizeOfView(isForSeries: Boolean) {

            val rate: Float = if (isForSeries) {
                0.85f
            } else {
                0.4f
            }

            val widthOfView = (Screen.width * rate).toInt()

            val rootParam = ViewGroup.LayoutParams(widthOfView, ViewGroup.LayoutParams.WRAP_CONTENT)
            dataBinding.root.layoutParams = rootParam

            if (isForSeries) {
                val set = ConstraintSet()
                set.clone(dataBinding.root as ConstraintLayout)
                set.setDimensionRatio(dataBinding.imgCoverCourse.id, "2:1")
                set.applyTo(dataBinding.root as ConstraintLayout)
            }

        }

    }


}