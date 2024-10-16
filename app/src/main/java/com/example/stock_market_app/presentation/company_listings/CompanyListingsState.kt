package com.example.stock_market_app.presentation.company_listings

import com.example.stock_market_app.domain.model.CompanyListing

data class CompanyListingsState(
    val companies:List<CompanyListing> =  emptyList(),
    val isLoading:Boolean=false,
    val isRefreshing:Boolean=false,
    val searchQuery:String=""
)
