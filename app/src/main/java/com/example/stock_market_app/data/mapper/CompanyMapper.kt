package com.example.stock_market_app.data.mapper

import com.example.stock_market_app.data.local.CompanyListingEntity
import com.example.stock_market_app.data.remote.dto.CompanyInfoDto
import com.example.stock_market_app.domain.model.CompanyInfo
import com.example.stock_market_app.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing():CompanyListing{
    return CompanyListing(
        name=name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity():CompanyListingEntity{
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyInfoDto.toCompanyInfo():CompanyInfo{
    return CompanyInfo(
        symbol=symbol?:"",
        description= description?:"",
        name=name?:"",
        country=country?:"",
        industry=industry?:""
    )
}