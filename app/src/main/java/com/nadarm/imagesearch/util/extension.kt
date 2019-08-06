package com.nadarm.imagesearch.util

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.SearchView

class MySearchView : SearchView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    interface Delegate {
        fun querySubmitted(query: String)
        fun queryChanged(text: String)
    }
}

fun SearchView.setOnQueryListener(delegate: MySearchView.Delegate) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            if (query != null) {
                delegate.querySubmitted(query)
                setQuery("", false)
                clearFocus()
            }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            val query = this@setOnQueryListener.query
            if (query.length >= 2) {
                delegate.queryChanged(query.toString())
            }
            return false
        }
    })
}