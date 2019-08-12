package com.nadarm.imagesearcher.presentation.view.adapter

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.CursorAdapter
import com.nadarm.imagesearcher.R
import kotlinx.android.synthetic.main.item_search_suggestion.view.*

class SuggestionCursorAdapter(
    private val searchView: SearchView,
    context: Context, c: Cursor?, autoRequery: Boolean
) : CursorAdapter(context, c, autoRequery) {

    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.item_search_suggestion, parent, false)
    }

    override fun bindView(view: View, context: Context, cursor: Cursor) {
        val query: String = cursor.getString(cursor.getColumnIndexOrThrow("_id"))
        view.suggestionTextView.text = query
        view.setOnClickListener { this.searchView.setQuery(query, true) }
    }
}