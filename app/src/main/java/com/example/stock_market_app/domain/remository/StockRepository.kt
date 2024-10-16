package com.example.stock_market_app.domain.remository

import com.example.stock_market_app.domain.model.CompanyListing
import com.example.stock_market_app.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getCompanyListings(fetchFromRemote:Boolean, query:String): Flow<Resource<List<CompanyListing>>>
}