package com.example.rickandmorty.ui.screens.characters

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.rickandmorty.model.Character
import com.example.rickandmorty.ui.navigation.Screen
import com.example.rickandmorty.viewmodel.CharacterViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.koin.androidx.compose.getViewModel
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import com.example.rickandmorty.data.model.FavoriteCharacter
import com.example.rickandmorty.ui.viewmodel.FavoriteViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(
    navController: NavController,
    characterViewModel: CharacterViewModel = getViewModel(),
    onBottomBarVisibilityChanged: (Boolean) -> Unit = {}
) {
    val characters by characterViewModel.characters.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()
    var previousIndex by remember { mutableStateOf(0) }

    LaunchedEffect(listState.firstVisibleItemIndex) {
        val visible = listState.firstVisibleItemIndex <= previousIndex
        previousIndex = listState.firstVisibleItemIndex
        onBottomBarVisibilityChanged(visible)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Персонажи",
                style = MaterialTheme.typography.titleLarge
            )

            Button(
                onClick = { navController.navigate("favorites") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3),
                    contentColor = Color.White
                )
            ) {
                Text("Избранное")
            }
        }

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = {
                isRefreshing = true
                characterViewModel.refreshCharacters {
                    isRefreshing = false
                }
            }
        ) {
            if (characters.isEmpty() && !isRefreshing) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
                    items(characters) { character ->
                        CharacterItem(
                            character = character,
                            onClick = {
                                navController.navigate(Screen.CharacterDetail.createRoute(character.id))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterItem(
    character: Character,
    onClick: () -> Unit = {},
    viewModel: FavoriteViewModel = getViewModel()
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (pressed) 1.05f else 1f)

    val scope = rememberCoroutineScope()
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(character.id) {
        isFavorite = viewModel.isFavorite(character.id)
    }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .scale(scale)
            .clickable {
                pressed = true
                onClick()
                pressed = false
            }
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(character.image),
                contentDescription = character.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )
            Spacer(Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(character.name, style = MaterialTheme.typography.titleMedium)
                Text("Status: ${character.status}", style = MaterialTheme.typography.bodyMedium)
                Text("Species: ${character.species}", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3),
                        contentColor = Color.White
                    ),
                    onClick = {
                        scope.launch {
                            val favoriteCharacter = FavoriteCharacter(
                                id = character.id,
                                name = character.name,
                                imageUrl = character.image
                            )

                            if (isFavorite) {
                                viewModel.removeFavorite(favoriteCharacter)
                            } else {
                                viewModel.addFavorite(favoriteCharacter)
                            }
                            isFavorite = !isFavorite
                        }
                    }
                ) {
                    Text(if (isFavorite) "Удалить из избранного" else "В избранное")
                }
            }
        }
    }
}