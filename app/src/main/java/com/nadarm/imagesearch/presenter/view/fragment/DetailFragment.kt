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
import com.nadarm.imagesearch.domain.model.ImageDocument
import com.nadarm.imagesearch.presenter.viewModel.DetailViewModel
import com.nadarm.imagesearch.util.loadImage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val compositeDisposable = CompositeDisposable()

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

        this.binding.detailVm = this.detailVm

        this.detailVm.outputs.loadImageDocument()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::setImageDocument)
            .addTo(compositeDisposable)
    }

    private fun setImageDocument(imageDocument: ImageDocument) {
        this.binding.imageDocument = imageDocument
    }

    companion object {
        @JvmStatic
        fun newInstance() = DetailFragment()
    }
}
