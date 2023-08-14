package com.example.starsship.domain.rp.satellite

import com.example.starsship.R
import com.example.starsship.data.Satellite
import com.example.starsship.data.SatellitesResponse
import com.example.starsship.domain.StarShipsResult
import com.example.starsship.domain.dao.SatelliteDao
import com.example.starsship.domain.rp.file.FileRepository
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SatelliteRepositoryImpl @Inject constructor(
    private val satelliteDao: SatelliteDao,
    private val fileRepository: FileRepository,
    private val jsonAdapter: JsonAdapter<SatellitesResponse>
) : SatelliteRepository {

    override suspend fun getSatellites(): StarShipsResult<List<Satellite>> {
        return withContext(Dispatchers.IO) {
            val json = fileRepository.getFileContent(R.raw.satellites)
            val satellites = jsonAdapter.fromJson(json)?.satellites?.toList()
            if (satellites.isNullOrEmpty()) {
                StarShipsResult.Error(IllegalArgumentException("Satellites not found"))
            } else {
                StarShipsResult.Success(satellites)
            }
        }
    }

    override suspend fun getSatellite(id: Int): StarShipsResult<Satellite> {
        return withContext(Dispatchers.IO) {
            val json = fileRepository.getFileContent(R.raw.satellite_detail)
            val satellite = jsonAdapter.fromJson(json)?.satellites?.find { x -> x.id == id }
            if (satellite == null) {
                StarShipsResult.Error(IllegalArgumentException("The satellite is not found"))
            } else {
                StarShipsResult.Success(satellite)
            }
        }
    }

    override suspend fun getSatelliteFromDb(id: Int): Satellite? {
        return withContext(Dispatchers.IO) {
            satelliteDao.getSatellite(id = id)
        }
    }

    override suspend fun saveSatellite(satellite: Satellite) {
        withContext(Dispatchers.IO) {
            satelliteDao.insert(satellite = satellite)
        }
    }
}