package com.example.stock_market_app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyListingEntity(
    val name:String,
    val symbol:String,
    val exchange:String,
    @PrimaryKey(autoGenerate = true) val id:Int?=null
)
