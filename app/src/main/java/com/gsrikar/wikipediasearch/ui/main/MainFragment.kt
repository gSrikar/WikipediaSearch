package com.gsrikar.wikipediasearch.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.google.android.material.snackbar.Snackbar
import com.gsrikar.wikipediasearch.BuildConfig
import com.gsrikar.wikipediasearch.R
import com.gsrikar.wikipediasearch.api.SearchApiInterface
import com.gsrikar.wikipediasearch.app.WikiApplication.Companion.getSearchDb
import com.gsrikar.wikipediasearch.model.respose.Pages
import com.gsrikar.wikipediasearch.network.Resource
import com.gsrikar.wikipediasearch.network.RetrofitProvider.retrofit
import com.gsrikar.wikipediasearch.network.Status
import com.gsrikar.wikipediasearch.search.SearchAdapter
import com.gsrikar.wikipediasearch.utils.ViewModelFactory
import kotlinx.android.synthetic.main.main_fragment.*


/**
 * Shows list of Wikipedia search items in a list
 */
class MainFragment : Fragment() {

    companion object {
        // Log cat tag
        private val TAG = MainFragment::class.java.simpleName
        // True for debug builds and false otherwise
        private val DBG = BuildConfig.DEBUG
    }

    private lateinit var viewModel: MainViewModel

    /**
     * Adapter shows the search results
     */
    private val adapter = SearchAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Get an instance of the search interface
        val searchApiInterface = retrofit?.create(SearchApiInterface::class.java)
        // Data access for Pages database
        val pageDao = getSearchDb().pageDao()
        // Create an instance of view model factory
        val viewModelFactory = ViewModelFactory(MainRepository(searchApiInterface, pageDao))
        // Get the Main View Model
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        setListeners()
        initRecyclerView()
    }

    private fun setListeners() {
        // Live data listeners
        viewModel.pagesLiveData.observe(
            viewLifecycleOwner,
            Observer {
                parsePagesData(it)
            }
        )
        // Search query listeners
        floatingSearchView.setOnQueryChangeListener { oldQuery, newQuery ->
            if (oldQuery == newQuery) {
                // Old and the new query are the same
                if (DBG) Log.d(TAG, "Old and the new query has not changed")
            } else if (newQuery.length < 3) {
                if (DBG) Log.d(TAG, "Query text is too small")
                // Reset the query
                viewModel.query = ""
                search()
            } else {
                viewModel.query = newQuery
                search()
            }
        }
    }

    /**
     * Initialize the Recycler view and the set the adapter
     */
    private fun initRecyclerView() {
        // Add the divider to the list
        searchRecyclerView.addItemDecoration(DividerItemDecoration(context, VERTICAL))
        // Set the adapter
        searchRecyclerView.adapter = adapter
    }

    /**
     * Parse the response received
     */
    private fun parsePagesData(resource: Resource<PagedList<Pages>>) {
        when (resource.status) {
            Status.LOADING -> if (DBG) Log.d(TAG, "Search Data is loading")
            Status.SUCCESS -> updateSearchList(resource.data)
            Status.ERROR -> {
                updateSearchList(resource.data)
                // Show error message
                Snackbar.make(main, resource.message.orEmpty(), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Search for the items
     */
    private fun search() {
        viewModel.query?.let { viewModel.search(it) }
    }

    /**
     * Updates the adapter list items
     */
    private fun updateSearchList(searchList: PagedList<Pages>?) {
        if (searchList.isNullOrEmpty()) {
            errorTextView.visibility = VISIBLE
        } else {
            if (DBG) Log.d(TAG, "Search List Size: ${searchList.size}")
            errorTextView.visibility = GONE
            adapter.submitList(searchList)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // Inflate the menu
        inflater.inflate(R.menu.search_menu, menu)
    }


}
