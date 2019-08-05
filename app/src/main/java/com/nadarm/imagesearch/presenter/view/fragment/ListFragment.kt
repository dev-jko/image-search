package com.nadarm.imagesearch.presenter.view.fragment

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.nadarm.imagesearch.R
import com.nadarm.imagesearch.databinding.FragmentListBinding
import com.nadarm.imagesearch.di.AndroidApplication
import com.nadarm.imagesearch.domain.model.ImageDocument
import com.nadarm.imagesearch.presenter.view.adapter.ImageAdapter
import com.nadarm.imagesearch.presenter.view.adapter.SealedViewHolderData
import com.nadarm.imagesearch.presenter.view.adapter.SuggestionCursorAdapter
import com.nadarm.imagesearch.presenter.viewModel.DetailViewModel
import com.nadarm.imagesearch.presenter.viewModel.ListViewModel
import com.nadarm.imagesearch.presenter.viewModel.SearchViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        this.binding.lifecycleOwner = this
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



        this.searchVm.outputs.querySuggestions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::updateSuggestions)
            .addTo(compositeDisposable)

        this.listVm.outputs.startDetailFragment()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::startDetailFragment)
            .addTo(compositeDisposable)

        this.listVm.outputs.itemList()
            .withLatestFrom(
                this.listVm.outputs.restorePosition(),
                BiFunction<List<SealedViewHolderData>, Int, Pair<List<SealedViewHolderData>, Int>> { documents, position ->
                    documents to position
                }
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::refreshDocuments)
            .addTo(compositeDisposable)

        this.listVm.outputs.displayProgress()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::displayProgress)
            .addTo(compositeDisposable)

        this.searchVm.outputs.query()
            .subscribeOn(Schedulers.io())
            .subscribe(this.listVm.inputs::query)
            .addTo(compositeDisposable)

        this.binding.imageRecyclerView
            .setOnScrollChangeListener { _, _, _, _, _ ->
                val layoutManager = this.binding.imageRecyclerView.layoutManager as GridLayoutManager
                val position = layoutManager.findFirstVisibleItemPosition()
                this.listVm.inputs.savePosition(position)
            }
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
                    is SealedViewHolderData.FooterItem -> count
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

    override fun onDestroy() {
        super.onDestroy()
        this.compositeDisposable.clear()
    }


    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }
}
