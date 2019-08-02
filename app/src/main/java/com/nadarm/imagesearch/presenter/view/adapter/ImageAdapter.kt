package com.nadarm.imagesearch.presenter.view.adapter

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nadarm.imagesearch.databinding.ImageItemBinding

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding:ImageItemBinding = DataBindingUtil.inflate()

        val viewHolder = ViewHolder()
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    class ViewHolder(binding: View) : RecyclerView.ViewHolder(binding.rootView) {

    }
}
