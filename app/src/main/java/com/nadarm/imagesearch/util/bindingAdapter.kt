package com.nadarm.imagesearch.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nadarm.imagesearch.domain.model.ImageDocument
import com.nadarm.imagesearch.presenter.view.adapter.ImageAdapter

@BindingAdapter("bindUrl")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view).load(url).fitCenter().into(view)
}

@BindingAdapter("bindDocuments")
fun changeDocuments(recyclerView: RecyclerView, documents: List<ImageDocument>) {
    (recyclerView.adapter as ImageAdapter).refresh(documents)
}
