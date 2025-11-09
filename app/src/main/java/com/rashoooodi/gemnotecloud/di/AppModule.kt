package com.rashoooodi.gemnotecloud.di
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.rashoooodi.gemnotecloud.data.local.AppDatabase
import com.rashoooodi.gemnotecloud.data.local.NoteDao
import com.rashoooodi.gemnotecloud.data.remote.GeminiClient
import com.rashoooodi.gemnotecloud.data.settings.GeminiSettingsStore
import com.rashoooodi.gemnotecloud.data.settings.geminiDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module @InstallIn(SingletonComponent::class) object AppModule {
  @Provides @Singleton fun provideDb(@ApplicationContext app: Context): AppDatabase =
    Room.databaseBuilder(app, AppDatabase::class.java, "gemnote.db").build()
  @Provides fun provideDao(db: AppDatabase): NoteDao = db.noteDao()
  @Provides @Singleton fun provideDataStore(@ApplicationContext app: Context): DataStore<Preferences> = app.geminiDataStore

  @Provides @Singleton fun provideSettingsStore(dataStore: DataStore<Preferences>): GeminiSettingsStore =
    GeminiSettingsStore(dataStore)

  @Provides @Singleton fun provideGemini(): GeminiClient = GeminiClient()
}
