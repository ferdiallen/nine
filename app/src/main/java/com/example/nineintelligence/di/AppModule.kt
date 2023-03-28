package com.example.nineintelligence.di

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.exoplayer.ExoPlayer
import androidx.work.WorkManager
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.core.Notification
import com.example.nineintelligence.core.WorkerTimer
import com.example.nineintelligence.data.network.apiservice.DetailUser
import com.example.nineintelligence.data.network.apiservice.GetListTryout
import com.example.nineintelligence.domain.repository.LoginUserImpl
import com.example.nineintelligence.data.network.apiservice.RegisterUser
import com.example.nineintelligence.domain.repository.RegisterUserImpl
import com.example.nineintelligence.data.network.apiservice.LoginUser
import com.example.nineintelligence.data.network.apiservice.UpdateProfile
import com.example.nineintelligence.domain.repository.DetailUserImpl
import com.example.nineintelligence.domain.repository.ListTryOutImpl
import com.example.nineintelligence.domain.repository.UpdateProfileImpl
import com.example.nineintelligence.domain.use_case.login_use_case.LoginUseCase
import com.example.nineintelligence.domain.use_case.profile_use_case.DetailProfileUseCase
import com.example.nineintelligence.domain.use_case.profile_use_case.UpdateProfileUseCase
import com.example.nineintelligence.domain.use_case.tryout_use_case.TryoutUseCase
import com.example.nineintelligence.navigation.NavigationViewModel
import com.example.nineintelligence.presentation.discuss.DiscussionViewModel
import com.example.nineintelligence.presentation.enter.EnterViewModel
import com.example.nineintelligence.presentation.enter.RegisterViewModel
import com.example.nineintelligence.presentation.profile.ProfileViewModel
import com.example.nineintelligence.presentation.subject.SubjectViewModel
import com.example.nineintelligence.presentation.tryout.TryoutViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

@OptIn(ExperimentalSerializationApi::class)
val appModule = module {
    factory {
        val audioAttributes = AudioAttributes.Builder().setUsage(C.USAGE_MEDIA)
            .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE).build()
        ExoPlayer.Builder(androidContext())
            .setAudioAttributes(audioAttributes, true)
            .build()
    }
    viewModel {
        DiscussionViewModel()
    }
    viewModel {
        SubjectViewModel()
    }
    single<RegisterUser> {
        RegisterUserImpl(get())
    }
    viewModel {
        RegisterViewModel(get())
    }
    single<LoginUser> {
        LoginUserImpl(get())
    }
    single {
        LoginUseCase(get())
    }
    single<DetailUser> {
        DetailUserImpl(get(), get())
    }
    single {
        DetailProfileUseCase(get())
    }
    single<UpdateProfile> {
        UpdateProfileImpl(get(), get())
    }
    single {
        UpdateProfileUseCase(get())
    }
    viewModel {
        EnterViewModel(get(), get())
    }
    viewModel {
        NavigationViewModel(get(), get())
    }
    viewModel {
        ProfileViewModel(get(), get(), get())
    }
    factory {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    explicitNulls = false
                })
            }
            install(Auth) {

            }
        }
    }
    single {
        PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ), migrations = listOf(SharedPreferencesMigration(get(), "AUTH_PREFS")),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = {
                androidContext().preferencesDataStoreFile("AUTH_PREFS")
            }
        )
    }
    single {
        AuthPrefs(get())
    }
    single {
        androidContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
    single {
        Notification(get())
    }
    single {
        androidContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }
    single<GetListTryout> {
        ListTryOutImpl(get())
    }
    single {
        TryoutUseCase(get())
    }
    viewModel {
        TryoutViewModel(get())
    }
    worker {
        WorkerTimer(androidContext(), get(), get())
    }
    single {
        WorkManager.getInstance(androidContext())
    }
}