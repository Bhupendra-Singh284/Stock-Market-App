package com.example.stock_market_app.data.csv

import java.io.InputStream

interface CSVParser <T>{
    suspend fun  parse(stream:InputStream):List<T>
}