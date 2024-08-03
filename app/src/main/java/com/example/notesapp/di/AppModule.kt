package com.example.notesapp.di

import android.app.Application
import com.example.notesapp.data.manager.LocalUserManagerImpl
import com.example.notesapp.domain.manager.LocalUserManager
import com.example.notesapp.domain.useCases.AppEntryUseCases
import com.example.notesapp.domain.useCases.ReadAppEntry
import com.example.notesapp.domain.useCases.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ) : LocalUserManager = LocalUserManagerImpl(application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    ) : AppEntryUseCases = AppEntryUseCases(
        saveAppEntry = SaveAppEntry(localUserManager),
        readAppEntry = ReadAppEntry(localUserManager)
    )

}