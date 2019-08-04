package com.nadarm.imagesearch.util

import android.content.Context
import android.util.AttributeSet
import android.widget.SearchView

class MySearchView : SearchView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

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
                clearFocus()
            }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            if (newText != null && newText.length >= 2) {
                delegate.queryChanged(newText)
            }
            return true
        }
    })
}