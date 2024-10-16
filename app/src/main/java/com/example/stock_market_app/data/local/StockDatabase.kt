package com.example.stock_market_app.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CompanyListingEntity::class], version = 1)
abstract class StockDatabase:RoomDatabase(){
    abstract val getDao:StockDao
}