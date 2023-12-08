

package com.example.android.todoapp.di

import android.content.Context
import androidx.room.Room
import com.example.android.todoapp.data.AppDatabase
import com.example.android.todoapp.support.di.DatabaseModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
object DatabaseTestModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .inMemoryDatabaseBuilder(context.applicationContext, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
}
