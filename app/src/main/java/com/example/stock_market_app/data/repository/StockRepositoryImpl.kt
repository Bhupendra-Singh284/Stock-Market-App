package com.example.stock_market_app.data.repository

import com.example.stock_market_app.data.csv.CSVParser
import com.example.stock_market_app.data.csv.IntradayInfoParser
import com.example.stock_market_app.data.local.StockDatabase
import com.example.stock_market_app.data.mapper.toCompanyInfo
import com.example.stock_market_app.data.mapper.toCompanyListing
import com.example.stock_market_app.data.mapper.toCompanyListingEntity
import com.example.stock_market_app.data.remote.StockApi
import com.example.stock_market_app.domain.model.CompanyInfo
import com.example.stock_market_app.domain.model.CompanyListing
import com.example.stock_market_app.domain.model.IntradayInfo
import com.example.stock_market_app.domain.remository.StockRepository
import com.example.stock_market_app.util.Resource
import com.opencsv.CSVReader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api:StockApi,
    private val db:StockDatabase,
    private val csvParser: CSVParser<CompanyListing>,
    private val intradayInfoParser: CSVParser<IntradayInfo>

): StockRepository {
        private val dao= db.getDao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings= dao.searchCompanyListing(query)
            emit(Resource.Success(data=localListings.map {
                it.toCompanyListing()
            }))
            val isDbEmpty= localListings.isEmpty()&&query.isBlank()
            val shouldJustLoadFromCache= !isDbEmpty && !fetchFromRemote
            if(shouldJustLoadFromCache)
            {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings= try{
                val response= api.getListings()
                csvParser.parse(response.byteStream())
            }catch (e:IOException){
                e.printStackTrace()
                emit(Resource.Error("Could't load data: IO Error"))
                null
            }catch (e:HttpException){
                e.printStackTrace()
                emit(Resource.Error("Could't load data: HTTP Error"))
                null
            }

            if(remoteListings!=null){
                dao.clearCompanyListings()
                dao.insertCompanyListings(
                    remoteListings.map { it.toCompanyListingEntity() }
                )
                emit(Resource.Success(
                    data= dao.searchCompanyListing("").map { it.toCompanyListing() }
                ))
                emit(Resource.Loading(false))

            }
        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val response= api.getIntradayInfo(symbol,"")
            val results=intradayInfoParser.parse(response.byteStream())
            Resource.Success(results)
        }catch (e:IOException){
            e.printStackTrace()
            Resource.Error(message = "Could not load intraday info")
        }catch (e:HttpException){
            e.printStackTrace()
            Resource.Error("Could not load intraday info")
        }

    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val results= api.getCompanyInfo(symbol,"")
            Resource.Success(results.toCompanyInfo())
        }catch (e:IOException){
            e.printStackTrace()
            Resource.Error(message = "Could not load intraday info")
        }catch (e:HttpException){
            e.printStackTrace()
            Resource.Error("Could not load intraday info")
        }
    }

}