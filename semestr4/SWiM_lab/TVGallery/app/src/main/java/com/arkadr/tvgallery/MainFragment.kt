package com.arkadr.tvgallery

import android.os.Bundle
import androidx.core.content.ContextCompat
import android.util.DisplayMetrics
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*

class MainFragment : BrowseSupportFragment() {

    private lateinit var mMetrics: DisplayMetrics
    private val photoFragment = PhotoFragment()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        prepareBackgroundManager()
        setupUIElements()
        loadRows()
        setupEventListeners()
    }

    private fun prepareBackgroundManager() {
        mMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(mMetrics)
    }

    private fun setupUIElements() {
        title = getString(R.string.browse_title)
        headersState = HEADERS_ENABLED

        isHeadersTransitionOnBackEnabled = true
        brandColor = ContextCompat.getColor(activity!!, R.color.fastlane_background)
    }

    private fun loadRows() {

        val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        val cardPresenter = CardPresenter()

        rowsAdapter.add(
            ListRow(HeaderItem(0, "Doggos"),
                ArrayObjectAdapter(cardPresenter).apply { addAll(0, PhotoList.shibes) })
        )


        rowsAdapter.add(
            ListRow(HeaderItem(1, "Cates"),
                ArrayObjectAdapter(cardPresenter).apply { addAll(0, PhotoList.cates) })
        )

        rowsAdapter.add(
            ListRow(HeaderItem(1, "Parrots"),
                ArrayObjectAdapter(cardPresenter).apply { addAll(0, PhotoList.parrots) })
        )

        adapter = rowsAdapter
    }

    private fun setupEventListeners() {
        onItemViewClickedListener = ItemViewClickedListener()
        onItemViewSelectedListener = ItemViewSelectedListener()
    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder,
            item: Any,
            rowViewHolder: RowPresenter.ViewHolder,
            row: Row
        ) {
            if (item is Photo) {
                fragmentManager?.beginTransaction()?.apply {
                    replace(R.id.main_browse_fragment, photoFragment)
                    addToBackStack(null)
                }?.commit()
            }
        }
    }

    private inner class ItemViewSelectedListener : OnItemViewSelectedListener {
        override fun onItemSelected(
            itemViewHolder: Presenter.ViewHolder?, item: Any?,
            rowViewHolder: RowPresenter.ViewHolder, row: Row
        ) {
            if (item is Photo) {
                photoFragment.setPhoto(item)
            }
        }
    }
}
