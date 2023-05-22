package com.hanisoft.bestar.di

import android.app.Application
import androidx.room.Room
import com.hanisoft.bestar.data.database.BeStarDataBase
import com.hanisoft.bestar.data.remote.BeStarApi
import com.hanisoft.bestar.domain.repository.DataBaseRepository
import com.hanisoft.bestar.domain.repository.DataBaseRepositoryImpl
import com.hanisoft.bestar.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLoginApi(): BeStarApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }


    @Provides
    @Singleton
    fun provideBeStarDatabase(app: Application):BeStarDataBase{
        return Room.databaseBuilder(app,
            BeStarDataBase::class.java,
            "beStar_db"
        ).build()
    }

    @Provides
    @Singleton
    fun ProvideDataBaseRepository(db:BeStarDataBase):DataBaseRepository{
        return DataBaseRepositoryImpl(db.compagionDao, db.userDao, db.historyDao)
    }


}