package com.example.rickandmorty.ui.screens.locations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rickandmorty.model.Location
import com.example.rickandmorty.ui.navigation.Screen
import com.example.rickandmorty.viewmodel.LocationViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.koin.androidx.compose.getViewModel

@Composable
fun LocationsScreen(
    navController: NavController,
    viewModel: LocationViewModel = getViewModel(),
    onBottomBarVisibilityChanged: (Boolean) -> Unit = {}
) {
    val locations by viewModel.locations.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()
    var previousIndex by remember { mutableStateOf(0) }

    LaunchedEffect(listState.firstVisibleItemIndex) {
        val visible = listState.firstVisibleItemIndex <= previousIndex
        previousIndex = listState.firstVisibleItemIndex
        onBottomBarVisibilityChanged(visible)
    }

    LaunchedEffect(Unit) {
        isRefreshing = true
        viewModel.fetchLocations()
        isRefreshing = false
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            isRefreshing = true
            viewModel.refreshLocations {
                isRefreshing = false
            }
        }
    ) {
        if (locations.isEmpty() && !isRefreshing) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
                items(locations) { location ->
                    LocationItem(location) {
                        navController.navigate(Screen.LocationDetail.createRoute(location.id))
                    }
                }
            }
        }
    }
}

@Composable
fun LocationItem(location: Location, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(location.name, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text("Type: ${location.type}", style = MaterialTheme.typography.bodyMedium)
            Text("Dimension: ${location.dimension}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}