package com.example.starsship.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.starsship.ui.detail.DetailScreen
import com.example.starsship.ui.list.ListScreen
import com.example.starsship.ui.theme.StarsShipTheme

@Composable
fun StarShipsApp() {
    StarsShipTheme {
        StarsShipNavGraph()
    }
}

@Composable
fun StarsShipNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = StarShipsDestinations.LIST,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = StarShipsDestinations.LIST) {
            ListScreen(navController = navController)
        }
        composable(
            route = StarShipsDestinations.DETAIL, arguments = listOf(
                navArgument(
                    StarShipsDestinations.SATELLITE_ID
                ) { type = NavType.StringType },
                navArgument(StarShipsDestinations.SATELLITE_NAME) {
                    type = NavType.StringType
                }
            )
        ) {
            DetailScreen()
        }
    }
}