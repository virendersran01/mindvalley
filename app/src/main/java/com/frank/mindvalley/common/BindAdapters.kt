package com.frank.mindvalley.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.frank.mindvalley.R


@BindingAdapter("app:loadImageFromUrl")
fun loadImageFromUrl(imageView: ImageView, url: String?) {
    url?.let {
        Glide.with(imageView.context).load(it).diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_mindvalley).into(imageView)
    }
}