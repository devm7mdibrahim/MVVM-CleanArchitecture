package com.aait.data.repository

import com.aait.data.datasource.HomeDataSource
import com.aait.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val homeDataSource: HomeDataSource) :
    HomeRepository {

}