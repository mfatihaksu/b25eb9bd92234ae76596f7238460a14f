package com.example.starsship.ui.list

import com.example.starsship.data.Satellite


data class ListViewState(val isLoading : Boolean = true, val satellites : MutableList<Satellite> = mutableListOf())