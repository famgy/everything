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

package com.famgy.everything.model.bean

import androidx.recyclerview.widget.DiffUtil

data class Policy(
    val id: Long,
    val name: String
) {
    companion object {
        val ALL: List<Policy> by lazy {
            NAMES.mapIndexed { index, name ->
                Policy(
                    (index + 1).toLong(),
                    name
                )
            }
        }

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Policy>() {
            override fun areItemsTheSame(oldItem: Policy, newItem: Policy): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Policy, newItem: Policy): Boolean {
                return oldItem == newItem
            }
        }

        val NAMES = listOf(
            "水印", "透明加解密", "截屏", "复制粘贴", "通讯录访问", "通话记录访问", "test1", "test2", "test3", "test4"
        )

    }
}
