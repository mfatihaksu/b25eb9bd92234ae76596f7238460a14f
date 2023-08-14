package com.example.starsship.ui.list

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starsship.data.Satellite
import com.example.starsship.domain.rp.satellite.SatelliteRepository
import com.example.starsship.domain.successOr
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val satelliteRepository: SatelliteRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(ListViewState())
    val uiState: StateFlow<ListViewState> = _uiState.asStateFlow()
    private var isFilterCalled : Boolean = false
    private val allSatellites = mutableStateListOf<Satellite>()

    init {
        getSatellites()
    }

    private fun getSatellites() {
        viewModelScope.launch {
            val satellitesDeferred = async { satelliteRepository.getSatellites() }
            val satellites = satellitesDeferred.await().successOr(emptyList())
            allSatellites.apply {
                addAll(satellites)
            }
            _uiState.update {
                it.copy(isLoading = false, satellites = satellites.toMutableList())
            }
        }
    }

    fun rollBackSatellites(){
        if (isFilterCalled){
            _uiState.update {
                it.copy(satellites = allSatellites)
            }
        }
    }

    fun searchSatellite(text : String){
        isFilterCalled = true
        val filteredList = _uiState.value.satellites.filter { x->x.name.orEmpty().lowercase().contains(text) }
        _uiState.update {
            it.copy(
                satellites = filteredList.toMutableList()
            )
        }
    }
}