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

package com.famgy.everything.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EdgeEffect
import androidx.core.view.doOnLayout
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.famgy.everything.databinding.ListItemPolicyBinding
import com.famgy.everything.model.bean.Policy


internal class PolicyAdapter : ListAdapter<Policy, RecyclerView.ViewHolder>(Policy.DIFF_CALLBACK) {

    /**
     * A [RecyclerView.OnScrollListener] to be set to the RecyclerView. This tilts the visible
     * items while the list is scrolled.
     *
     * @see RecyclerView.addOnScrollListener
     */
    val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            recyclerView.forEachVisibleHolder { holder: PolicyViewHolder ->
                holder.rotation
                    // Update the velocity.
                    // The velocity is calculated by the horizontal scroll offset.
                    .setStartVelocity(holder.currentVelocity - dx * SCROLL_ROTATION_MAGNITUDE)
                    // Start the animation. This does nothing if the animation is already running.
                    .start()
            }
        }
    }

    /**
     * A [RecyclerView.EdgeEffectFactory] to be set to the RecyclerView. This adds bounce effect
     * when the list is over-scrolled.
     *
     * @see RecyclerView.setEdgeEffectFactory
     */
    val edgeEffectFactory = object : RecyclerView.EdgeEffectFactory() {
        override fun createEdgeEffect(recyclerView: RecyclerView, direction: Int): EdgeEffect {
            return object : EdgeEffect(recyclerView.context) {

                override fun onPull(deltaDistance: Float) {
                    super.onPull(deltaDistance)
                    handlePull(deltaDistance)
                }

                override fun onPull(deltaDistance: Float, displacement: Float) {
                    super.onPull(deltaDistance, displacement)
                    handlePull(deltaDistance)
                }

                private fun handlePull(deltaDistance: Float) {
                    // This is called on every touch event while the list is scrolled with a finger.
                    // We simply update the view properties without animation.
                    val sign = if (direction == DIRECTION_RIGHT) -1 else 1
                    val rotationDelta = sign * deltaDistance * OVERSCROLL_ROTATION_MAGNITUDE
                    val translationXDelta =
                        sign * recyclerView.width * deltaDistance * OVERSCROLL_TRANSLATION_MAGNITUDE
                    recyclerView.forEachVisibleHolder { holder: PolicyViewHolder ->
                        holder.rotation.cancel()
                        holder.translationX.cancel()
                        holder.itemView.rotation += rotationDelta
                        holder.itemView.translationX += translationXDelta
                    }
                }

                override fun onRelease() {
                    super.onRelease()
                    // The finger is lifted. This is when we should start the animations to bring
                    // the view property values back to their resting states.
                    recyclerView.forEachVisibleHolder { holder: PolicyViewHolder ->
                        holder.rotation.start()
                        holder.translationX.start()
                    }
                }

                override fun onAbsorb(velocity: Int) {
                    super.onAbsorb(velocity)
                    val sign = if (direction == DIRECTION_RIGHT) -1 else 1
                    // The list has reached the edge on fling.
                    val translationVelocity = sign * velocity * FLING_TRANSLATION_MAGNITUDE
                    recyclerView.forEachVisibleHolder { holder: PolicyViewHolder ->
                        holder.translationX
                            .setStartVelocity(translationVelocity)
                            .start()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PolicyViewHolder {
        return PolicyViewHolder(ListItemPolicyBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            .apply {
                // The rotation pivot should be at the center of the top edge.
                itemView.doOnLayout { v -> v.pivotX = v.width / 2f }
                itemView.pivotY = 0f
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val policy = getItem(position)

        (holder as PolicyViewHolder).bind(policy)
    }


    class PolicyViewHolder(
        private val binding: ListItemPolicyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        var currentVelocity = 0f

        /**
         * A [SpringAnimation] for this RecyclerView item. This animation rotates the view with a bouncy
         * spring configuration, resulting in the oscillation effect.
         *
         * The animation is started in [CheeseAdapter.onScrollListener].
         */
        val rotation: SpringAnimation = SpringAnimation(itemView, SpringAnimation.ROTATION)
            .setSpring(
                SpringForce()
                    .setFinalPosition(0f)
                    .setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY)
                    .setStiffness(SpringForce.STIFFNESS_LOW)
            )
            .addUpdateListener { _, _, velocity ->
                currentVelocity = velocity
            }

        /**
         * A [SpringAnimation] for this RecyclerView item. This animation is used to bring the item back
         * after the over-scroll effect.
         */
        val translationX: SpringAnimation = SpringAnimation(itemView, SpringAnimation.TRANSLATION_X)
            .setSpring(
                SpringForce()
                    .setFinalPosition(0f)
                    .setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY)
                    .setStiffness(SpringForce.STIFFNESS_LOW)
            )

        fun bind(item: Policy) {
            binding.apply {
                policy = item
                executePendingBindings()
            }
        }
    }
}




/**
 * Runs [action] on every visible [RecyclerView.ViewHolder] in this [RecyclerView].
 */
private inline fun <reified T : RecyclerView.ViewHolder> RecyclerView.forEachVisibleHolder(
    action: (T) -> Unit
) {
    for (i in 0 until childCount) {
        action(getChildViewHolder(getChildAt(i)) as T)
    }
}

// The constants below are used to calculate the animation magnitude from values taken from UI
// events. Their values are determined empirically and can be modified to change the animation
// flavor.

/** The magnitude of rotation while the list is scrolled. */
private const val SCROLL_ROTATION_MAGNITUDE = 0.25f
/** The magnitude of rotation while the list is over-scrolled. */
private const val OVERSCROLL_ROTATION_MAGNITUDE = -10
/** The magnitude of translation distance while the list is over-scrolled. */
private const val OVERSCROLL_TRANSLATION_MAGNITUDE = 0.2f
/** The magnitude of translation distance when the list reaches the edge on fling. */
private const val FLING_TRANSLATION_MAGNITUDE = 0.5f
