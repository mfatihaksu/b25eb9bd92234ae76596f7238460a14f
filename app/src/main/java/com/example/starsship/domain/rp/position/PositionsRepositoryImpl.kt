package com.example.starsship.domain.rp.position

import com.example.starsship.R
import com.example.starsship.data.SatellitePositions
import com.example.starsship.data.SatellitePositionsResponse
import com.example.starsship.domain.StarShipsResult
import com.example.starsship.domain.rp.file.FileRepository
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PositionsRepositoryImpl @Inject constructor(
    private val fileRepository: FileRepository,
    private val jsonAdapter: JsonAdapter<SatellitePositionsResponse>
) : PositionRepository {
    override suspend fun getPositions(id: Int): StarShipsResult<SatellitePositions?> {
        return withContext(Dispatchers.IO){
            val json = fileRepository.getFileContent(R.raw.positions)
            val satellitePositions = jsonAdapter.fromJson(json)?.list?.find { x->x.id == id.toString() }
            if (satellitePositions == null){
                StarShipsResult.Error(IllegalArgumentException("Satellite Positions not found"))
            }else{
                StarShipsResult.Success(satellitePositions)
            }
        }
    }
}