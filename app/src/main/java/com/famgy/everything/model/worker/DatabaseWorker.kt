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

package com.famgy.everything.model.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.famgy.everything.common.SONG_DATA_FILENAME
import com.famgy.everything.model.AppDatabase
import com.famgy.everything.model.bean.Song
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader

import kotlinx.coroutines.coroutineScope

class DatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        Log.e("===doWork===", "+++++++ Welcome doWork ++++++++")

        val appDatabase =
            AppDatabase.getInstance(
                applicationContext
            )

        try {
            applicationContext.assets.open(SONG_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val songType = object : TypeToken<List<Song>>() {}.type
                    val songList: List<Song> = Gson().fromJson(jsonReader, songType)
                    appDatabase.songDao().insertAll(songList)
                }
            }


            Result.success()
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private val TAG = DatabaseWorker::class.java.simpleName
    }
}