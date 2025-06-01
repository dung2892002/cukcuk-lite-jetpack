package com.example.cukcuk.data.repository

import com.example.cukcuk.data.local.dao.StatisticDao
import com.example.cukcuk.domain.repository.StatisticRepository
import javax.inject.Inject


class StatisticRepositoryImpl @Inject constructor(
    private val dao: StatisticDao
) : StatisticRepository {
}