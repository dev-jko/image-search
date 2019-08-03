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
    }
}