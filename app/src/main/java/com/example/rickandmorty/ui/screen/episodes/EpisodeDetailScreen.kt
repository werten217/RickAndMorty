package com.example.rickandmorty.ui.screens.episodes

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rickandmorty.viewmodel.EpisodeViewModel
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeDetailScreen(
    navController: NavController,
    episodeId: Int,
    viewModel: EpisodeViewModel = getViewModel()
) {
    val episode by viewModel.episodeDetail.collectAsState()

    LaunchedEffect(episodeId) {
        viewModel.fetchEpisodeDetail(episodeId)
    }

    Column {
        TopAppBar(
            title = { Text(episode?.name ?: "Episode") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )

        if (episode == null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            val e = episode!!
            Column(modifier = Modifier.padding(16.dp)) {
                Text(e.name, style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(8.dp))
                Text("Air date: ${e.air_date}", style = MaterialTheme.typography.bodyLarge)
                Text("Episode: ${e.episode}", style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(16.dp))
                Text("Characters:", style = MaterialTheme.typography.titleMedium)
                e.characters.forEach { characterUrl ->
                    Text(characterUrl, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
