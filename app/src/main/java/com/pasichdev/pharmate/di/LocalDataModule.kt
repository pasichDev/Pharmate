package com.pasichdev.pharmate.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.pasichdev.pharmate.data.datastore.LocalDatastore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


private const val DATASTORE_NAME = "pharamate_set"
private val Context.dataStore by preferencesDataStore(DATASTORE_NAME)

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideDataStore(context: Context): DataStore<Preferences> {
        return context.dataStore
    }


    @Provides
    @Singleton
    fun provideLocalDatastore(
        dataStore: DataStore<Preferences>
    ): LocalDatastore = LocalDatastore(dataStore)

}

