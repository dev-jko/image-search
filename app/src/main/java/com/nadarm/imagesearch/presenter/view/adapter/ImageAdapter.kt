package com.nadarm.imagesearch.presenter.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nadarm.imagesearch.R
import com.nadarm.imagesearch.databinding.ImageItemBinding
import com.nadarm.imagesearch.domain.model.ImageDocument

class ImageAdapter(
    private val delegate: Delegate
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    interface Delegate : ViewHolder.Delegate

    private val documents: ArrayList<ImageDocument> = ArrayList()


    fun refresh(newDocuments: List<ImageDocument>) {
        this.documents.clear()
        this.documents.addAll(newDocuments)
        notifyDataSetChanged()
    }

    fun addDocuments(newDocuments: List<ImageDocument>) {
        this.documents.addAll(newDocuments)
        notifyItemRangeInserted(this.documents.size, newDocuments.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ImageItemBinding = DataBindingUtil.inflate(inflater, R.layout.image_item, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = this.documents.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.documents[position], this.delegate)
    }


    class ViewHolder(
        private val binding: ImageItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        interface Delegate {
            fun imageRequested(imageView: ImageView, document: ImageDocument)
            fun documentClicked(document: ImageDocument)
        }

        fun bind(document: ImageDocument, delegate: Delegate) {
            delegate.imageRequested(binding.imageView, document)
            binding.root.setOnClickListener { delegate.documentClicked(document) }
        }

    }
}
