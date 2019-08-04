package com.nadarm.imagesearch.presenter.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nadarm.imagesearch.R
import com.nadarm.imagesearch.databinding.ImageListItemBinding
import com.nadarm.imagesearch.domain.model.ImageDocument

class ImageAdapter(
    private val delegate: Delegate
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    interface Delegate : ViewHolder.Delegate

    private var documents: List<ImageDocument> = emptyList()


    fun refresh(newDocuments: List<ImageDocument>) {
        this.documents = newDocuments
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ImageListItemBinding = DataBindingUtil.inflate(inflater, R.layout.image_list_item, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = this.documents.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.documents[position], this.delegate)
    }


    class ViewHolder(
        private val binding: ImageListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(document: ImageDocument, delegate: Delegate) {
            binding.imageDocument = document
            binding.delegate = delegate
        }

        interface Delegate {
            fun imageClicked(imageDocument: ImageDocument)
        }
    }
}
