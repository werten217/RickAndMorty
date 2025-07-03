package com.example.rickandmorty.ui.screens.episodes

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
import com.example.rickandmorty.model.Episode
import com.example.rickandmorty.ui.navigation.Screen
import com.example.rickandmorty.viewmodel.EpisodeViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.koin.androidx.compose.getViewModel

@Composable
fun EpisodesScreen(
    navController: NavController,
    viewModel: EpisodeViewModel = getViewModel(),
    onBottomBarVisibilityChanged: (Boolean) -> Unit = {}
) {
    val episodes by viewModel.episodes.collectAsState()
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
        viewModel.fetchEpisodes()
        isRefreshing = false
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            isRefreshing = true
            viewModel.fetchEpisodes()
            isRefreshing = false
        }
    ) {
        if (episodes.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
                items(episodes) { episode ->
                    EpisodeItem(episode) {
                        navController.navigate(Screen.EpisodeDetail.createRoute(episode.id))
                    }
                }
            }
        }
    }
}

@Composable
fun EpisodeItem(episode: Episode, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(episode.name, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text("Air date: ${episode.air_date}", style = MaterialTheme.typography.bodyMedium)
            Text("Episode: ${episode.episode}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}