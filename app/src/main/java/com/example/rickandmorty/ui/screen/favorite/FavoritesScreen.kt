package com.example.rickandmorty.ui.screens.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rickandmorty.ui.navigation.Screen
import com.example.rickandmorty.ui.viewmodel.FavoriteViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: FavoriteViewModel = koinViewModel()
) {
    val favorites by viewModel.favorites.collectAsState()

    Column {
        TopAppBar(title = { Text("Избранное") })

        LazyColumn {
            items(favorites) { character ->
                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            navController.navigate(Screen.CharacterDetail.createRoute(character.id))
                        }
                ) {
                    Row(Modifier.padding(16.dp)) {
                        Text(character.name)
                        Spacer(Modifier.weight(1f))
                        Button(onClick = { viewModel.removeFavorite(character) },

                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2196F3),
                                contentColor = Color.White
                            )

                        ) {
                            Text("Удалить")
                        }
                    }
                }
            }
        }
    }
}