

package com.example.android.todoapp.support.di

import android.content.Context
import androidx.room.Room
import com.example.android.todoapp.data.common.AppDatabase
import com.example.android.todoapp.data.task.DefaultTaskRepository
import com.example.android.todoapp.data.task.TaskRepository
import com.example.android.todoapp.data.task.source.local.TaskDao
import com.example.android.todoapp.data.task.source.remote.DefaultRemoteTaskDataSource
import com.example.android.todoapp.data.task.source.remote.RemoteTaskDataSource
import com.example.android.todoapp.data.user.DefaultUserRepository
import com.example.android.todoapp.data.user.UserRepository
import com.example.android.todoapp.data.user.source.local.DefaultLocalUserDataSource
import com.example.android.todoapp.data.user.source.local.LocalUserDataSource
import com.example.android.todoapp.data.user.source.remote.DefaultRemoteUserDataSource
import com.example.android.todoapp.data.user.source.remote.RemoteUserDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindTaskRepository(repository: DefaultTaskRepository): TaskRepository

    @Singleton
    @Binds
    abstract fun bindUserRepository(repository: DefaultUserRepository): UserRepository

}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindRemoteTaskDataSource(dataSource: DefaultRemoteTaskDataSource): RemoteTaskDataSource

    @Singleton
    @Binds
    abstract fun bindRemoteUserDataSource(dataSource: DefaultRemoteUserDataSource): RemoteUserDataSource

    @Singleton
    @Binds
    abstract fun bindLocalUserDataSource(dataSource: DefaultLocalUserDataSource): LocalUserDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "Tasks.db"
        ).build()
    }

    @Provides
    fun provideTaskDao(database: AppDatabase): TaskDao = database.taskDao()
}
