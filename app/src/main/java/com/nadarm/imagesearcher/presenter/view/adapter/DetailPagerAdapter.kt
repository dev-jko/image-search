package com.nadarm.imagesearcher.presenter.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.nadarm.imagesearcher.domain.model.ImageDocument
import com.nadarm.imagesearcher.presenter.view.fragment.DetailPagerItem

class DetailPagerAdapter(
    fm: FragmentManager,
    private val delegate: Delegate
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var imageDocuments: List<ImageDocument> = emptyList()

    fun refresh(documents: List<ImageDocument>) {
        this.imageDocuments = documents
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {

        return DetailPagerItem(imageDocuments[position], delegate)
    }

    override fun getCount(): Int = this.imageDocuments.size

    interface Delegate : DetailPagerItem.Delegate
}