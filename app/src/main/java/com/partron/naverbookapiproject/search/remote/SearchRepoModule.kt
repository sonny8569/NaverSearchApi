package com.partron.naverbookapiproject.search.remote

import com.partron.naverbookapiproject.search.repo.Repository
import com.partron.naverbookapiproject.search.repo.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchRepoModule {
    @Provides
    @Singleton
    fun provideSearchRepo(): Repository {
        return RepositoryImpl()
    }
}