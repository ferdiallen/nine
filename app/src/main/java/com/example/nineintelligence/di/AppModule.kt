package com.example.nineintelligence.di

import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.nineintelligence.presentation.discuss.DiscussionViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<Player> {
        ExoPlayer.Builder(androidContext()).build()
    }
    viewModel {
        DiscussionViewModel(get())
    }
}