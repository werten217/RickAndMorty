package com.example.rickandmorty.ui.screens.locations

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rickandmorty.viewmodel.LocationViewModel
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDetailScreen(
    navController: NavController,
    locationId: Int,
    viewModel: LocationViewModel = getViewModel()
) {
    val location by viewModel.locationDetail.collectAsState()

    LaunchedEffect(locationId) {
        viewModel.fetchLocationDetail(locationId)
    }

    Column {
        TopAppBar(
            title = { Text(location?.name ?: "Location") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )

        if (location == null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            val loc = location!!
            Column(modifier = Modifier.padding(16.dp)) {
                Text(loc.name, style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(8.dp))
                Text("Type: ${loc.type}", style = MaterialTheme.typography.bodyLarge)
                Text("Dimension: ${loc.dimension}", style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(16.dp))
                Text("Residents:", style = MaterialTheme.typography.titleMedium)
                loc.residents.forEach { residentUrl ->
                    Text(residentUrl, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
