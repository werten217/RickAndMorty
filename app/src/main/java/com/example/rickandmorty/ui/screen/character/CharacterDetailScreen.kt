package com.example.rickandmorty.ui.screens.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.rickandmorty.viewmodel.CharacterViewModel
import org.koin.androidx.compose.getViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    navController: NavController,
    characterId: Int,
    viewModel: CharacterViewModel = getViewModel()
) {

    val character by viewModel.getCharacterDetail(characterId).collectAsState()

    Column {
        TopAppBar(
            title = { Text(character?.name ?: "Character") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )

        if (character == null) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            val c = character!!
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(c.image),
                    contentDescription = c.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(CircleShape)
                )
                Spacer(Modifier.height(16.dp))
                Text(c.name, style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(8.dp))
                Text("Status: ${c.status}", style = MaterialTheme.typography.bodyLarge)
                Text("Species: ${c.species}", style = MaterialTheme.typography.bodyLarge)
                Text("Gender: ${c.gender}", style = MaterialTheme.typography.bodyLarge)
                Text("Origin: ${c.origin.name}", style = MaterialTheme.typography.bodyLarge)
                Text("Last known location: ${c.location.name}", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}