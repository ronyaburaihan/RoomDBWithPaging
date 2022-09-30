package com.example.roomdbwithpaging.di

import android.content.Context
import androidx.room.Room
import com.example.roomdbwithpaging.data.database.ContactDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            ContactDatabase::class.java,
            "contact_db"
        ).build()

    @Singleton
    @Provides
    fun provideContactDao(database: ContactDatabase) = database.contactDao()
}