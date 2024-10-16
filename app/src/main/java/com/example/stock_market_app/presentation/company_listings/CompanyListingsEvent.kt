package com.example.stock_market_app.presentation.company_listings

sealed class CompanyListingsEvent {
    object Refresh:CompanyListingsEvent()
    data class OnSearchQueryChange(val query:String):CompanyListingsEvent()
}