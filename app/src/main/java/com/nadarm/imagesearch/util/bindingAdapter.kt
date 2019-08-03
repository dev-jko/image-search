package com.nadarm.imagesearch.util

import android.widget.ImageView
import android.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nadarm.imagesearch.domain.model.ImageDocument
import com.nadarm.imagesearch.presenter.view.adapter.ImageAdapter

@BindingAdapter("bindUrl")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view).load(url).fitCenter().into(view)
}

//@BindingAdapter("bindDocuments", "bindRecently", requireAll = true)
@BindingAdapter("bindDocuments")
fun changeDocuments(
    recyclerView: RecyclerView,
    documents: List<ImageDocument>
//    recentlyDocuments: List<ImageDocument>
) {
//    val newDocuments = listOf<ImageDocument>(recentlyDocuments, documents)
    (recyclerView.adapter as ImageAdapter).refresh(documents)
}

@BindingAdapter("querySubmitted")
fun submitQuery(searchView: SearchView, delegate: MySearchView.Delegate) {
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            if (query != null) {
                delegate.querySubmitted(query)
                searchView.clearFocus()
            }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }
    })
}
