package com.example.stock_market_app.data.repository

import com.example.stock_market_app.data.csv.CSVParser
import com.example.stock_market_app.data.local.StockDatabase
import com.example.stock_market_app.data.mapper.toCompanyListing
import com.example.stock_market_app.data.mapper.toCompanyListingEntity
import com.example.stock_market_app.data.remote.StockApi
import com.example.stock_market_app.domain.model.CompanyListing
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
    private val csvParser: CSVParser<CompanyListing>
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

}