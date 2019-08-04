package com.nadarm.imagesearch.presenter.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.nadarm.imagesearch.R
import com.nadarm.imagesearch.databinding.FragmentDetailBinding
import com.nadarm.imagesearch.di.AndroidApplication
import com.nadarm.imagesearch.presenter.view.adapter.DetailPagerAdapter
import com.nadarm.imagesearch.presenter.viewModel.DetailViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject


class DetailFragment : Fragment() {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var binding: FragmentDetailBinding
    @Inject
    lateinit var detailVm: DetailViewModel.ViewModelImpl

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

        val pagerAdapter: DetailPagerAdapter = DetailPagerAdapter(childFragmentManager)
        this.binding.detailViewPager.adapter = pagerAdapter

        this.binding.detailVm = this.detailVm

        this.detailVm.outputs.imageDocuments()
            .subscribe {
                pagerAdapter.refresh(it)
                pagerAdapter.notifyDataSetChanged()
            }
            .addTo(compositeDisposable)

        this.detailVm.outputs.currentPageAndIndex()
            .subscribe { this.binding.detailViewPager.currentItem = it.second }
            .addTo(compositeDisposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.compositeDisposable.clear()
    }

    companion object {
        @JvmStatic
        fun newInstance() = DetailFragment()
    }
}
