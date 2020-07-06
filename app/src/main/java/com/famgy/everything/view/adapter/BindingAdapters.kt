
package com.famgy.everything.views.adapters

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("appImageFromUrl")
fun bindImageFromUrl(view: AppCompatImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .into(view)
    }
}

@BindingAdapter("appImageFromId")
fun bindImageFromUrl(view: AppCompatImageView, imageId: Int) {
    if (0 != imageId) {
        Glide.with(view.context)
            .load(imageId)
            .into(view)
    }
}
