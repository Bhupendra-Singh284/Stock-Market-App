package com.example.stock_market_app.presentation.company_info

import com.example.stock_market_app.domain.model.CompanyInfo
import com.example.stock_market_app.domain.model.IntradayInfo

data class CompanyInfoState(
    val stockInfo: List<IntradayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
