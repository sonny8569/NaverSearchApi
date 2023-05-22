package com.partron.naverbookapiproject.ViewModel.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.partron.naverbookapiproject.Https.Repository
import com.partron.naverbookapiproject.ViewModel.SearchViewModel

class SearchViewModelFactory (private val repository: Repository) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchViewModel :: class.java)){
            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("UnKnow ViewModel class")
    }

}