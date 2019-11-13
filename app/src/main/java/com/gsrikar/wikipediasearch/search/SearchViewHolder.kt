package com.gsrikar.wikipediasearch.search

import android.view.View
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_LONG
import com.gsrikar.wikipediasearch.R
import com.gsrikar.wikipediasearch.ui.detail.DetailFragment
import com.gsrikar.wikipediasearch.ui.main.MainFragmentDirections
import kotlinx.android.synthetic.main.search_list_item.view.*


/**
 * Holds views used by Search Adapter
 */
class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener { openDetailFragment() }
    }

    /**
     * Thumbnail image
     */
    val pageImage: SimpleDraweeView = itemView.pageImageView

    /**
     * Page title
     */
    val pageTitle: TextView = itemView.pageTitle

    /**
     * Page description
     */
    val pageDescription: TextView = itemView.pageDescription

    /**
     * Source url
     */
    var pageId: Int? = null

    /**
     * Opens the [DetailFragment]
     */
    private fun openDetailFragment() {
        pageId?.let {
            itemView.findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDetailFragment(it)
            )
        } ?: kotlin.run {
            Snackbar.make(itemView, R.string.empty_source, LENGTH_LONG).show()
        }
    }

}