package com.famgy.everything.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.famgy.everything.model.bean.Song
import com.famgy.everything.model.repository.SongRepository

class SongListViewModel internal constructor(songRepository: SongRepository) : ViewModel() {

    val songs: LiveData<List<Song>> = songRepository.getAllSongs()
}