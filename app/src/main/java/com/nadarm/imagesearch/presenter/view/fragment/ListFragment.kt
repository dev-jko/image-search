package com.nadarm.imagesearch.presenter.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.nadarm.imagesearch.R


class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }


    private fun loadImage(imageView: ImageView, imageUrl: String) {
        Glide.with(this).load(imageUrl).placeholder()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }
}
