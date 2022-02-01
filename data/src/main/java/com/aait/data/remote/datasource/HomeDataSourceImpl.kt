package com.aait.data.remote.datasource

import com.aait.data.datasource.HomeDataSource
import com.aait.data.remote.endpoints.HomeEndPoints
import javax.inject.Inject

class HomeDataSourceImpl @Inject constructor(private val homeEndPoints: HomeEndPoints) :
    HomeDataSource {
}