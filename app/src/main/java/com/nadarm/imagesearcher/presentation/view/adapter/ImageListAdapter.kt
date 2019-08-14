package com.nadarm.imagesearcher.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nadarm.imagesearcher.BR
import com.nadarm.imagesearcher.R
import com.nadarm.imagesearcher.presentation.model.SealedViewHolderData

class ImageListAdapter : ListAdapter<SealedViewHolderData, ImageListAdapter.ViewHolder>(ItemCallback()) {

    interface Delegate : SealedViewHolderData.ImageItem.Delegate, SealedViewHolderData.FooterOneBtnItem.Delegate

    override fun getItemViewType(position: Int): Int {
        return when (this.getItem(position)) {
            is SealedViewHolderData.ImageItem -> R.layout.item_image_list
            is SealedViewHolderData.HeaderItem -> R.layout.item_header_list
            is SealedViewHolderData.FooterOneBtnItem -> R.layout.item_footer_one_btn_list
            is SealedViewHolderData.FooterTwoBtnItem -> R.layout.item_footer_two_btn_list
        }
    }

    fun getItemViewClass(position: Int): SealedViewHolderData {
        return this.getItem(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(inflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.getItem(position))
    }

    class ViewHolder(
        private val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SealedViewHolderData) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
        }
    }

    class ItemCallback : DiffUtil.ItemCallback<SealedViewHolderData>() {
        override fun areItemsTheSame(oldItem: SealedViewHolderData, newItem: SealedViewHolderData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SealedViewHolderData, newItem: SealedViewHolderData): Boolean {
            return when {
                oldItem is SealedViewHolderData.HeaderItem
                        && newItem is SealedViewHolderData.HeaderItem -> oldItem.text == newItem.text
                oldItem is SealedViewHolderData.ImageItem
                        && newItem is SealedViewHolderData.ImageItem -> oldItem.imageDocument.imageUrl == newItem.imageDocument.imageUrl
                else -> false
            }
        }
    }
}


