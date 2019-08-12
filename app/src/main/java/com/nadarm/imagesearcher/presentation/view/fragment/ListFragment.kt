package com.nadarm.imagesearcher.presentation.view.fragment

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.nadarm.imagesearcher.R
import com.nadarm.imagesearcher.databinding.FragmentListBinding
import com.nadarm.imagesearcher.di.AndroidApplication
import com.nadarm.imagesearcher.di.AppSchedulers
import com.nadarm.imagesearcher.domain.model.ImageDocument
import com.nadarm.imagesearcher.presentation.model.SealedViewHolderData
import com.nadarm.imagesearcher.presentation.view.adapter.ImageAdapter
import com.nadarm.imagesearcher.presentation.view.adapter.SuggestionCursorAdapter
import com.nadarm.imagesearcher.presentation.viewModel.DetailViewModel
import com.nadarm.imagesearcher.presentation.viewModel.ListViewModel
import com.nadarm.imagesearcher.presentation.viewModel.SearchViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.withLatestFrom
import javax.inject.Inject


class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val compositeDisposable = CompositeDisposable()
    @Inject
    lateinit var listVm: ListViewModel.ViewModelImpl
    @Inject
    lateinit var searchVm: SearchViewModel.ViewModelImpl
    @Inject
    lateinit var detailVm: DetailViewModel.ViewModelImpl
    @Inject
    lateinit var schedulers: AppSchedulers

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        this.binding.lifecycleOwner = viewLifecycleOwner
        return this.binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity!!.application as AndroidApplication).getAppComponent().inject(this)

        this.binding.adapter = ImageAdapter()
        this.binding.listVm = this.listVm
        this.binding.searchVm = this.searchVm
        this.binding.searchView.suggestionsAdapter =
            SuggestionCursorAdapter(this.binding.searchView, context!!, null, false)
        val searchAutoCompleteTextView = this.binding.searchView
            .findViewById(R.id.search_src_text) as AutoCompleteTextView
        searchAutoCompleteTextView.setDropDownBackgroundResource(R.color.noColor)

        this.binding.imageRecyclerView
            .setOnScrollChangeListener { _, _, _, _, _ ->
                val layoutManager = this.binding.imageRecyclerView.layoutManager as GridLayoutManager
                val position = layoutManager.findFirstVisibleItemPosition()
                this.listVm.inputs.savePosition(position)
            }

        this.searchVm.outputs.querySuggestions()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .subscribe(this::updateSuggestions, this::printLog)
            .addTo(compositeDisposable)

        this.listVm.outputs.startDetailFragment()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .subscribe(this::startDetailFragment, this::printLog)
            .addTo(compositeDisposable)

        this.listVm.outputs.itemList()
            .withLatestFrom(this.listVm.outputs.restorePosition()) { documents: List<SealedViewHolderData>, position: Int ->
                documents to position
            }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .subscribe(this::refreshDocuments, this::printLog)
            .addTo(compositeDisposable)

        this.listVm.outputs.displayProgress()
            .observeOn(schedulers.ui())
            .subscribe(this::displayProgress, this::printLog)
            .addTo(compositeDisposable)

        this.searchVm.outputs.query()
            .subscribeOn(schedulers.io())
            .subscribe(this.listVm.inputs::query, this::printLog)
            .addTo(compositeDisposable)

        this.listVm.outputs.showSnackBar()
            .observeOn(schedulers.ui())
            .subscribe(this::showSnackBar, this::printLog)
            .addTo(compositeDisposable)
    }


    private fun showSnackBar(unit: Unit) {
        Snackbar.make(this.binding.root, "Network Error!!", Snackbar.LENGTH_INDEFINITE)
            .setAction("Try Again") { this.listVm.inputs.retrySearch() }
            .show()
    }

    private fun printLog(throwable: Throwable) {
        Log.e(this.tag, "Do something with Error Log : $throwable.message")
    }

    private fun updateSuggestions(suggestions: Cursor) {
        this.binding.searchView.suggestionsAdapter.changeCursor(suggestions)
        this.binding.searchView.suggestionsAdapter.notifyDataSetChanged()
    }

    private fun selectSpanCount(size: Int) {
        when {
            size <= 40 -> this.setGridLayoutSpanCount(2)
            else -> this.setGridLayoutSpanCount(3)
        }
    }

    private fun setGridLayoutSpanCount(count: Int) {
        if (count !in 1..4) {
            return
        }

        (this.binding.imageRecyclerView.layoutManager as GridLayoutManager).spanCount = count
        val layoutManager = (this.binding.imageRecyclerView.layoutManager as GridLayoutManager)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (binding.adapter!!.getItemViewClass(position)) {
                    is SealedViewHolderData.ImageItem -> 1
                    is SealedViewHolderData.HeaderItem -> count
                    is SealedViewHolderData.FooterOneBtnItem -> count
                    is SealedViewHolderData.FooterTwoBtnItem -> count
                }
            }
        }
    }

    private fun refreshDocuments(itemListAndPosition: Pair<List<SealedViewHolderData>, Int>) {
        this.selectSpanCount(itemListAndPosition.first.size)
        this.binding.adapter?.refresh(itemListAndPosition.first)
        this.binding.imageRecyclerView.scrollToPosition(itemListAndPosition.second)
    }

    private fun displayProgress(visibility: Int) {
        this.binding.listProgressBar.visibility = visibility
    }

    private fun startDetailFragment(imageDocument: ImageDocument) {
        if (this.findNavController().currentDestination!!.id == R.id.listFragment) {
            this.detailVm.inputs.selectedImage(imageDocument)
            this.findNavController().navigate(R.id.action_listFragment_to_detailFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.compositeDisposable.clear()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }
}
