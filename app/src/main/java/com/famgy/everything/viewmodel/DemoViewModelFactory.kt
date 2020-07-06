
package com.famgy.everything.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.famgy.everything.model.bean.Demo
import com.famgy.everything.model.repository.SongRepository


/**
 * Factory for creating a [SongListViewModel] with a constructor that takes a [SongRepository].
 */
class DemoViewModelFactory(
    private val demo: Demo
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = DemoViewModel(demo) as T
}
