package com.famgy.everything.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.famgy.everything.common.uitils.InjectorUtil
import com.famgy.everything.databinding.FragmentSongListBinding
import com.famgy.everything.view.adapter.SongAdapter
import com.famgy.everything.viewmodel.SongListViewModel


class SongListFragment : Fragment() {

    private val viewModel: SongListViewModel by viewModels {
        InjectorUtil.provideSongListViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSongListBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = SongAdapter()
        binding.songList.adapter = adapter
        subscribeUi(adapter)

        return binding.root
    }

    private fun subscribeUi(adapter: SongAdapter) {
        viewModel.songs.observe(viewLifecycleOwner) { songs ->
            Log.e("===TEST===", "songs : " + songs.toString())
            adapter.submitList(songs)
        }
    }
}