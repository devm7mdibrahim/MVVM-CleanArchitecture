package com.devm7mdibrahim.data.remote.datasource

import com.devm7mdibrahim.data.datasource.HomeDataSource
import com.devm7mdibrahim.data.remote.endpoints.HomeEndPoints
import javax.inject.Inject

class HomeDataSourceImpl @Inject constructor(private val homeEndPoints: HomeEndPoints) :
    HomeDataSource {
}