package com.example.starsship.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starsship.data.Position
import com.example.starsship.data.Satellite
import com.example.starsship.domain.rp.position.PositionRepository
import com.example.starsship.domain.rp.satellite.SatelliteRepository
import com.example.starsship.domain.successOr
import com.example.starsship.ui.StarShipsDestinations.SATELLITE_ID
import com.example.starsship.ui.StarShipsDestinations.SATELLITE_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val satelliteRepository: SatelliteRepository,
    private val positionRepository: PositionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailViewState())
    val uiState: StateFlow<DetailViewState> = _uiState.asStateFlow()

    init {
        val id: String = checkNotNull(savedStateHandle[SATELLITE_ID])
        val name: String = checkNotNull(savedStateHandle[SATELLITE_NAME])
        getSatellite(id.toInt(), name)
        getSatellitePositions(id.toInt())
    }

    private fun getSatellite(id: Int, name: String) {
        viewModelScope.launch {
            val isExistsDeferred = async { isSatelliteExists(id) }
            val isExists = isExistsDeferred.await()
            if (isExists == null){
                val satelliteDeferred = async { satelliteRepository.getSatellite(id) }
                val satellite = satelliteDeferred.await().successOr(null)
                satellite?.name = name
                satellite?.let {
                    saveSatellite(it)
                    updateUIState(it)
                }
            }else{
                updateUIState(satellite = isExists)
            }
        }
    }

    private fun updateUIState(satellite: Satellite){
        _uiState.update {
            it.copy(
                satellite = satellite,
                isLoading = false
            )
        }
    }

    private fun getSatellitePositions(id : Int) {
        viewModelScope.launch {
            val satellitePositionDeferred = async { positionRepository.getPositions(id) }
            val satellitePositions = satellitePositionDeferred.await().successOr(null)
            if (satellitePositions != null){
                _uiState.update {
                    it.copy(
                        satellitePositions = satellitePositions
                    )
                }
                if (satellitePositions.positions.isNullOrEmpty().not()){
                    setFirstPosition(satellitePositions.positions?.first())
                }
            }
        }
    }

    private fun setFirstPosition(position: Position?){
        _uiState.update {
            it.copy(
                currentPosition = position,
                currentPositionIndex = 0
            )
        }
    }

    fun getCurrentPosition(){
        val newPosition = _uiState.value.currentPositionIndex + 1
        if (newPosition  == _uiState.value.satellitePositions?.positions?.size){
            setFirstPosition(_uiState.value.satellitePositions?.positions?.first())
            return
        }
        _uiState.update {
            it.copy(
                currentPosition = _uiState.value.satellitePositions?.positions?.get(newPosition),
                currentPositionIndex = newPosition
            )
        }
    }

    private suspend fun isSatelliteExists(id: Int): Satellite? {
        return satelliteRepository.getSatelliteFromDb(id)
    }

    private suspend fun saveSatellite(satellite : Satellite){
        satelliteRepository.saveSatellite(satellite = satellite)
    }
}