package com.example.starsship.di

import android.content.Context
import androidx.room.Room
import com.example.starsship.data.SatellitePositionsResponse
import com.example.starsship.data.SatellitesResponse
import com.example.starsship.db.AppDatabase
import com.example.starsship.domain.dao.SatelliteDao
import com.example.starsship.domain.rp.file.FileRepository
import com.example.starsship.domain.rp.file.FileRepositoryImpl
import com.example.starsship.domain.rp.position.PositionRepository
import com.example.starsship.domain.rp.position.PositionsRepositoryImpl
import com.example.starsship.domain.rp.satellite.SatelliteRepository
import com.example.starsship.domain.rp.satellite.SatelliteRepositoryImpl
import com.example.starsship.util.Constants.DB_NAME
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    fun provideSatelliteDao(appDatabase: AppDatabase): SatelliteDao {
        return appDatabase.satelliteDao()
    }

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    fun provideSatellitesJsonAdapter(moshi: Moshi): JsonAdapter<SatellitesResponse> =
        moshi.adapter(SatellitesResponse::class.java)

    @Provides
    fun providePositionsJsonAdapter(moshi: Moshi): JsonAdapter<SatellitePositionsResponse> =
        moshi.adapter(SatellitePositionsResponse::class.java)

    @Provides
    fun provideFileRepository(@ApplicationContext context: Context) : FileRepository = FileRepositoryImpl(context = context)

    @Provides
    fun providePositionRepository(fileRepository: FileRepository, jsonAdapter : JsonAdapter<SatellitePositionsResponse>) :
            PositionRepository = PositionsRepositoryImpl(fileRepository = fileRepository, jsonAdapter = jsonAdapter)
    @Provides
    fun provideSatelliteRepository(fileRepository: FileRepository, jsonAdapter : JsonAdapter<SatellitesResponse>, satelliteDao: SatelliteDao) :
            SatelliteRepository = SatelliteRepositoryImpl(fileRepository = fileRepository, jsonAdapter = jsonAdapter, satelliteDao = satelliteDao)
}