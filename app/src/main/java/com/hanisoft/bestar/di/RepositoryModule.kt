package com.hanisoft.bestar.di

import com.hanisoft.bestar.data.repository.BeStarRepositoryImpl
import com.hanisoft.bestar.domain.repository.BeStarRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        beStarRepositoryImpl: BeStarRepositoryImpl
    ): BeStarRepository
}