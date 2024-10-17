package com.example.stock_market_app.data.remote.dto

import java.sql.Timestamp

data class IntradayInfoDto(
    val timestamp: String,
    val close: Double
)
