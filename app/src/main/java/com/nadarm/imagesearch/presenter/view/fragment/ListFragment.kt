package com.nadarm.imagesearch.presenter.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nadarm.imagesearch.AndroidApplication
import com.nadarm.imagesearch.R
import com.nadarm.imagesearch.databinding.FragmentListBinding
import com.nadarm.imagesearch.presenter.view.adapter.ImageAdapter
import com.nadarm.imagesearch.presenter.viewModel.ListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject


class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var listVm: ListViewModel.ViewModelImpl

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        this.binding.lifecycleOwner = this
        return this.binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity!!.application as AndroidApplication).getAppComponent().inject(this)

        this.binding.adapter = ImageAdapter(this.listVm)
        this.binding.listVm = this.listVm

        this.listVm.outputs.startDetailFragment()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { this.startDetailFragment() }
            .addTo(compositeDisposable)
    }

    private fun startDetailFragment() {
        if (this.findNavController().currentDestination!!.id == R.id.listFragment) {
            this.findNavController().navigate(R.id.action_listFragment_to_detailFragment)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }
}
