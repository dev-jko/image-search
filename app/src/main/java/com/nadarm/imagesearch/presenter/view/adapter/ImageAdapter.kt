package com.nadarm.imagesearch.presenter.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.nadarm.imagesearch.BR
import com.nadarm.imagesearch.R

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    interface Delegate : SealedViewHolderData.ImageItem.Delegate

    private var itemList: List<SealedViewHolderData> = emptyList()


    fun refresh(newDocuments: List<SealedViewHolderData>) {
        this.itemList = newDocuments
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position]) {
            is SealedViewHolderData.ImageItem -> R.layout.item_image_list
            is SealedViewHolderData.HeaderItem -> R.layout.item_header_list
            is SealedViewHolderData.FooterItem -> R.layout.item_footer_list
        }
    }

    fun getItemViewClass(position: Int): SealedViewHolderData {
        return this.itemList[position]
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(inflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.itemList[position])
    }

    override fun getItemCount(): Int = this.itemList.size


    class ViewHolder(
        private val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SealedViewHolderData) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
        }
    }

}

