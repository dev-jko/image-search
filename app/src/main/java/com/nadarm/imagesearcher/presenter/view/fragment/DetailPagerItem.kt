package com.nadarm.imagesearcher.presenter.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.nadarm.imagesearcher.R
import com.nadarm.imagesearcher.databinding.ItemDetailPagerBinding
import com.nadarm.imagesearcher.domain.model.ImageDocument

class DetailPagerItem(
    private val imageDocument: ImageDocument,
    private val delegate: Delegate
) : Fragment() {

    private lateinit var binding: ItemDetailPagerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.item_detail_pager, container, false)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.binding.imageDocument = this.imageDocument
        this.binding.delegate = this.delegate

        val info = this.binding.infoLayout
        this.binding.root.setOnClickListener {
            if (info.visibility == View.GONE) {
                info.visibility = View.VISIBLE
            } else {
                info.visibility = View.GONE
            }
        }

    }


    interface Delegate {
        fun linkClicked(url: String)
    }

}