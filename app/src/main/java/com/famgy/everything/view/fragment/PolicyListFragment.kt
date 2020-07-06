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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.famgy.everything.databinding.FragmentPolicyListBinding
import com.famgy.everything.view.adapter.PolicyAdapter
import com.famgy.everything.viewmodel.PolicyListViewModel


/**
 * A fragment representing a single Policy list screen.
 */
class PolicyListFragment : Fragment() {

    private val viewModel: PolicyListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPolicyListBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val policyList: RecyclerView = binding.policyList

        val policyAdapter = PolicyAdapter()

        policyList.adapter = policyAdapter

        // The adapter knows how to animate its items while the list is scrolled.
        policyList.addOnScrollListener(policyAdapter.onScrollListener)
        policyList.edgeEffectFactory = policyAdapter.edgeEffectFactory

        viewModel.policies.observe(viewLifecycleOwner) { policies ->
            policyAdapter.submitList(policies)
        }

        return binding.root
    }
}
