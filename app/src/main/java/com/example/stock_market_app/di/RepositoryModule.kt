package com.example.stock_market_app.di

import com.example.stock_market_app.data.csv.CSVParser
import com.example.stock_market_app.data.csv.CompanyListingParser
import com.example.stock_market_app.data.repository.StockRepositoryImpl
import com.example.stock_market_app.domain.model.CompanyListing
import com.example.stock_market_app.domain.remository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsCompanyListingsParser(companyListingParser: CompanyListingParser): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(repository:StockRepositoryImpl):StockRepository

}