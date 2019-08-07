package com.nadarm.imagesearcher.util

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.nadarm.imagesearcher.R
import com.nadarm.imagesearcher.domain.model.ImageDocument

@BindingAdapter("bindUrl", "bindProgressBar", requireAll = false)
fun loadImage(view: ImageView, url: String?, progressBar: ProgressBar?) {
    if (url != null) {
        progressBar?.visibility = View.VISIBLE
        Glide.with(view).load(url).fitCenter().listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar?.visibility = View.GONE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar?.visibility = View.GONE
                return false
            }
        })
            .error(R.drawable.error)
            .into(view)
    }
}

@BindingAdapter("queryListener")
fun setOnQueryListener(searchView: SearchView, delegate: MySearchView.Delegate) {
    searchView.setOnQueryListener(delegate)
}


@BindingAdapter("siteNameOrDocUrl")
fun setText(textView: TextView, imageDocument: ImageDocument) {
    if (imageDocument.displaySiteName.isNotEmpty()) {
        textView.text = imageDocument.displaySiteName
    } else {
        textView.text = imageDocument.docUrl
    }
}