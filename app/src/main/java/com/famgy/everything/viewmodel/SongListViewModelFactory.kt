
package com.famgy.everything.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.famgy.everything.model.repository.SongRepository


/**
 * Factory for creating a [SongListViewModel] with a constructor that takes a [SongRepository].
 */
class SongListViewModelFactory(
    private val repository: SongRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = SongListViewModel(repository) as T
}
