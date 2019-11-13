package com.gsrikar.wikipediasearch.ui.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.gsrikar.wikipediasearch.model.respose.Pages
import com.gsrikar.wikipediasearch.network.Resource


/**
 * View model takes the requests from the [MainFragment], passes it to [MainRepository] and
 * returns the response from [MainRepository] to [MainFragment]
 */
class MainViewModel(private val repository: MainRepository) : ViewModel() {

    /**
     * Query typed
     */
    var query: String? = null

    /**
     * Mediator Live data listens to the changes in the [MainRepository.pagesMutableLiveData]
     */
    private val pagesMutableLiveData = MediatorLiveData<Resource<PagedList<Pages>>>()

    init {
        pagesMutableLiveData.addSource(repository.pagesMutableLiveData) {
            pagesMutableLiveData.value = it
        }
    }

    /**
     * Contains the Pages Live data
     */
    val pagesLiveData = pagesMutableLiveData as LiveData<Resource<PagedList<Pages>>>

    /**
     * Search for the item
     */
    fun search(query: String) {
        repository.search(query)
    }
}
