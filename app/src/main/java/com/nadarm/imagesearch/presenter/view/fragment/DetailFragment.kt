package com.nadarm.imagesearch.presenter.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.nadarm.imagesearch.AndroidApplication
import com.nadarm.imagesearch.R
import com.nadarm.imagesearch.databinding.FragmentDetailBinding
import com.nadarm.imagesearch.presenter.viewModel.ListViewModel
import javax.inject.Inject


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    @Inject lateinit var listVm: ListViewModel.ViewModelImpl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        this.binding.lifecycleOwner = this
        return this.binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity!!.application as AndroidApplication).getAppComponent().inject(this)

        this.binding.listVm = this.listVm
    }

    companion object {
        @JvmStatic
        fun newInstance() = DetailFragment()
    }
}
