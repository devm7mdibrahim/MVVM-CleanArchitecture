package com.devm7mdibrahim.data.repository

import com.devm7mdibrahim.data.datasource.HomeDataSource
import com.devm7mdibrahim.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val homeDataSource: HomeDataSource) :
    HomeRepository {

}