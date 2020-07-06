/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.famgy.everything.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.famgy.everything.R
import com.famgy.everything.common.uitils.InjectorUtil
import com.famgy.everything.databinding.FragmentDemoBinding
import com.famgy.everything.viewmodel.DemoViewModel

/**
 * A fragment representing a single Demo detail screen.
 */
class DemoFragment : Fragment() {

    private val args: DemoFragmentArgs by navArgs()

    private val demoViewModel: DemoViewModel by viewModels {
        InjectorUtil.provideDemoViewModelFactory(args.demo)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentDemoBinding>(
            inflater, R.layout.fragment_demo, container, false
        ).apply {
            viewModel = demoViewModel

            toolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }
        }

        switchFragment(args.demo.id.toInt())

        return binding.root
    }

    private fun switchFragment(id: Int) {
        val detailFragment = demoViewModel.detailFragments.get(id)
        detailFragment?.let {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.demo_detail_fragment, detailFragment)
                .commit()
        }
    }
}
