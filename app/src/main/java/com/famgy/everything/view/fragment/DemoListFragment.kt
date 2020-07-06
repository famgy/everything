/*
 * Copyright 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.famgy.everything.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.famgy.everything.R
import com.famgy.everything.databinding.FragmentDemoListBinding
import com.famgy.everything.view.adapter.DemoAdapter
import com.famgy.everything.viewmodel.DemoListViewModel

class DemoListFragment : Fragment() {

    private val viewModel: DemoListViewModel by viewModels()
    private var pickUpElevation: Float = 0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDemoListBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val demoList: RecyclerView = binding.demoList

        pickUpElevation = resources.getDimensionPixelSize(R.dimen.pick_up_elevation).toFloat()

        // The ItemTouchHelper handles view drag inside the RecyclerView.
        val itemTouchHelper = ItemTouchHelper(touchHelperCallback)
        itemTouchHelper.attachToRecyclerView(demoList)

        val demoAdapter = DemoAdapter(
            onClickListener = { demo ->
                val directions =
                    HomeViewPagerFragmentDirections.actionHomeViewPagerFragmentToDemoFragment(demo)

                findNavController().navigate(directions)
            },
            onLongClickListener = { holder ->
                // Start dragging the item when it is long-pressed.
                itemTouchHelper.startDrag(holder)
            }
        )

        demoList.adapter = demoAdapter
        viewModel.demos.observe(viewLifecycleOwner) { demos ->
            Log.e("===TEST===", "demos : " + demos.toString())
            demoAdapter.submitList(demos)
        }

        return binding.root
    }

    private val touchHelperCallback = object : ItemTouchHelper.Callback() {

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return makeMovementFlags(
                // We allow items to be dragged in any direction.
                ItemTouchHelper.UP
                        or ItemTouchHelper.DOWN
                        or ItemTouchHelper.LEFT
                        or ItemTouchHelper.RIGHT,
                // But not swiped away.
                0
            )
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            // Reorder the items in the ViewModel. The ViewModel will then notify the UI through the
            // LiveData.
            viewModel.move(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            // Do nothing
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            super.onSelectedChanged(viewHolder, actionState)
            val view = viewHolder?.itemView ?: return
            when (actionState) {
                ItemTouchHelper.ACTION_STATE_DRAG -> {
                    ViewCompat.animate(view).setDuration(150L).translationZ(pickUpElevation)
                }
            }
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            ViewCompat.animate(viewHolder.itemView).setDuration(150L).translationZ(0f)
        }

        override fun isLongPressDragEnabled(): Boolean {
            // We handle the long press on our side for better touch feedback.
            return false
        }

        override fun isItemViewSwipeEnabled(): Boolean {
            return false
        }
    }

}
