

package com.example.android.todoapp.di

import com.example.android.todoapp.data.FakeTaskRepository
import com.example.android.todoapp.data.task.TaskRepository
import com.example.android.todoapp.support.di.RepositoryModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
object RepositoryTestModule {

    @Singleton
    @Provides
    fun provideTasksRepository(): TaskRepository {
        return FakeTaskRepository()
    }
}
