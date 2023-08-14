package com.example.starsship.ui.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.starsship.R
import com.example.starsship.data.Satellite
import com.example.starsship.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavController, viewModel: ListViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(
                text = stringResource(id = R.string.satellites),
                style = MaterialTheme.typography.titleLarge
            )
        }, actions = {})
    }) { innerPadding ->
        BoxWithConstraints {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
                    .padding(innerPadding),
                contentAlignment = Alignment.TopCenter
            ) {
                val uiState = viewModel.uiState.collectAsState()
                if (uiState.value.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        SearchBar(modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .shadow(elevation = 0.dp, shape = RoundedCornerShape(6.dp))
                            .padding(horizontal = 16.dp),
                            onSearch = {
                                if (it.length >= 3) {
                                    viewModel.searchSatellite(it)
                                }
                            },
                            rollBackList = {
                                viewModel.rollBackSatellites()
                            })
                        Spacer(modifier = Modifier.height(32.dp))
                        LazyColumn(modifier = Modifier
                            .fillMaxWidth()
                            .height(this@BoxWithConstraints.maxHeight)
                            .padding(horizontal = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            state = rememberLazyListState(),
                            content = {
                                items(uiState.value.satellites.size, itemContent = {
                                    SatelliteCard(
                                        satellite = uiState.value.satellites[it],
                                        showDetail = { id, name ->
                                            navController.navigate("detail/${id}/${name}")
                                        })
                                    Divider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(1.dp),
                                        thickness = 1.dp,
                                        color = Color.Black
                                    )
                                })
                            })
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier, onSearch: (String) -> Unit, rollBackList: () -> Unit) {
    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    TextField(
        value = text,
        onValueChange = {
            text = it
            if (it.length <= 2) {
                rollBackList()
            }
        },
        label = { Text("Search") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onSearch(text)
            keyboardController?.hide()
            focusManager.clearFocus()
        })
    )
}

@Composable
private fun SatelliteCard(satellite: Satellite, showDetail: (String, String?) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 4.dp)
            .background(color = Color.White, shape = RoundedCornerShape(0.dp))
            .clickable {
                showDetail(satellite.id.toString(), satellite.name)
            }
    ) {
        SatelliteItem(modifier = Modifier.align(Alignment.Center), satellite = satellite)
    }
}

@Composable
private fun SatelliteItem(modifier: Modifier, satellite: Satellite) {
    Row(modifier = modifier) {
        var icon: Int = R.drawable.ic_red_light
        var status: String = stringResource(id = R.string.passive)
        var textColor = Color.LightGray
        if (satellite.active == true) {
            icon = R.drawable.ic_green_light
            status = stringResource(id = R.string.active)
            textColor = Color.Black
        }
        Image(painter = painterResource(id = icon), contentDescription = null)
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = satellite.name.orEmpty(), color = textColor, style = Typography.titleMedium)
            Text(text = status, color = textColor, style = Typography.labelSmall)
        }
    }
}