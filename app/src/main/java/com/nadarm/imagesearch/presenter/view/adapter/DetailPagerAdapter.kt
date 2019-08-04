package com.nadarm.imagesearch.presenter.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.nadarm.imagesearch.domain.model.ImageDocument
import com.nadarm.imagesearch.presenter.view.fragment.DetailPagerItem

class DetailPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var imageDocuments: List<ImageDocument> = emptyList()

    fun refresh(documents: List<ImageDocument>) {
        this.imageDocuments = documents
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {

        return DetailPagerItem(imageDocuments[position])
    }

    override fun getCount(): Int = this.imageDocuments.size

    interface Delegate {
        fun firstIndexReached()
        fun lastIndexReached()
    }

}