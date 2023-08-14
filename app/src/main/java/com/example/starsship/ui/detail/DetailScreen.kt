package com.example.starsship.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.starsship.R
import com.example.starsship.ui.theme.Typography
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(viewModel: DetailViewModel = hiltViewModel()) {

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(
                text = stringResource(id = R.string.detail),
                style = MaterialTheme.typography.titleLarge
            )
        }, actions = {})
    }) { innerPadding ->
        BoxWithConstraints {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)){
                val uiState = viewModel.uiState.collectAsState()
                if (uiState.value.isLoading){
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }else{
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = uiState.value.satellite?.name.orEmpty(), color = Color.Black , style = Typography.titleLarge)
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(text = uiState.value.satellite?.firstFlight.orEmpty(), color = Color.LightGray , style = Typography.labelMedium)
                        Spacer(modifier = Modifier.height(48.dp))
                        Text(text = stringResource(id = R.string.height_mass , uiState.value.satellite?.height.toString(), uiState.value.satellite?.mass.toString()), color = Color.Black, style = Typography.labelLarge)
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(text = stringResource(id = R.string.cost , uiState.value.satellite?.costPerLaunch.toString()))
                        LaunchedEffect(Unit) {
                            while(true) {
                                delay(3000)
                                viewModel.getCurrentPosition()
                            }
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(text = stringResource(id = R.string.last_position , uiState.value.currentPosition?.posX.toString() , uiState.value.currentPosition?.posY.toString()))
                    }
                }
            }
        }
    }
}