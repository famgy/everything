package com.famgy.everything.common.uitils

import android.content.Context
import com.famgy.everything.model.AppDatabase
import com.famgy.everything.model.bean.Demo
import com.famgy.everything.model.repository.SongRepository
import com.famgy.everything.viewmodel.DemoViewModelFactory
import com.famgy.everything.viewmodel.LoginViewModelFactory
import com.famgy.everything.viewmodel.SongListViewModelFactory

object InjectorUtil {

    fun provideLoginViewModelFactory(): LoginViewModelFactory {
        return LoginViewModelFactory()
    }

    fun provideSongListViewModelFactory(context: Context): SongListViewModelFactory {
        val repository =  SongRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).songDao()
        )

        return SongListViewModelFactory(repository)
    }

    fun provideDemoViewModelFactory(
        demo: Demo
    ): DemoViewModelFactory {

        return DemoViewModelFactory(demo)
    }
}