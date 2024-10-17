package com.example.stock_market_app.domain.model

import java.time.LocalDateTime

data class IntradayInfo(
    val date:LocalDateTime,
    val close:Double
)
