package com.example.stock_market_app.di

import android.app.Application
import androidx.room.Room
import com.example.stock_market_app.data.local.StockDatabase
import com.example.stock_market_app.data.remote.StockApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStockApi(): StockApi {
        return Retrofit.Builder().baseUrl(StockApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create()).build().create()
    }

    @Provides
    @Singleton
    fun providesStockDatabase(application: Application): StockDatabase {
        return Room.databaseBuilder(application, StockDatabase::class.java, "stockdb.db").build()
    }

}