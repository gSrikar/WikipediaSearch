package com.gsrikar.wikipediasearch.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.gsrikar.wikipediasearch.R
import com.gsrikar.wikipediasearch.model.respose.Pages


/**
 * Adapter shows the list of search items in a vertical scrollable way
 */
class SearchAdapter : PagedListAdapter<Pages, SearchViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_list_item, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        getItem(position)?.let {
            // Update the UI elements
            holder.pageImage.setImageURI(it.thumbnail?.source)
            holder.pageTitle.text = it.title
            holder.pageDescription.text = it.terms?.description?.getOrNull(0)
            holder.pageId = it.pageId
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Pages>() {
            override fun areItemsTheSame(
                oldItem: Pages,
                newItem: Pages
            ): Boolean {
                return oldItem.pageId == oldItem.pageId
            }

            override fun areContentsTheSame(
                oldItem: Pages,
                newItem: Pages
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}