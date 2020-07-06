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

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import com.famgy.everything.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Demo(
    val id: Long,
    val name: String,
    @DrawableRes
    val image: Int
) : Parcelable {
    companion object {
        val ALL: List<Demo> by lazy {
            NAMES.mapIndexed { index, name ->
                Demo(
                    (index + 1).toLong(),
                    name,
                    IMAGES[index]
                )
            }
        }

        val NAMES = listOf(
            "水印", "透明加解密", "截屏", "复制粘贴", "通讯录访问", "通话记录访问", "通话记录访问"
        )

        val IMAGES = listOf(
            R.drawable.pokemon_001,
            R.drawable.pokemon_002,
            R.drawable.pokemon_003,
            R.drawable.pokemon_004,
            R.drawable.pokemon_005,
            R.drawable.pokemon_006,
            R.drawable.pokemon_007
        )

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Demo>() {
            override fun areItemsTheSame(oldItem: Demo, newItem: Demo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Demo, newItem: Demo): Boolean {
                return oldItem == newItem
            }
        }
    }

//    fun getImageUrl(): String {
//        return "https://pokeres.bastionbot.org/images/pokemon/$id.png"
//    }
}