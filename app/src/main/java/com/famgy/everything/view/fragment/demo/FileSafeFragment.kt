package com.famgy.everything.view.fragment.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.famgy.everything.R
import com.famgy.everything.databinding.FragmentDemoDetailFilesafeBinding

class FileSafeFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentDemoDetailFilesafeBinding>(
            inflater, R.layout.fragment_demo_detail_filesafe, container, false
        ).apply {

        }

        return binding.root
    }
}