package com.example.rickandmorty.ui.screens.characters

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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.rickandmorty.model.Character
import com.example.rickandmorty.ui.navigation.Screen
import com.example.rickandmorty.viewmodel.CharacterViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.koin.androidx.compose.getViewModel
import androidx.compose.animation.core.animateFloatAsState

@Composable
fun CharactersScreen(
    navController: NavController,
    viewModel: CharacterViewModel = getViewModel(),
    onBottomBarVisibilityChanged: (Boolean) -> Unit = {}
) {
    val characters by viewModel.characters.collectAsState()
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
        viewModel.fetchCharacters()
        isRefreshing = false
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            isRefreshing = true
            viewModel.fetchCharacters()
            isRefreshing = false
        }
    ) {
        if (characters.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
                items(characters) { character ->
                    CharacterItem(character) {
                        navController.navigate(Screen.CharacterDetail.createRoute(character.id))
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterItem(character: Character, onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (pressed) 1.05f else 1f)

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .scale(scale)
            .clickable(
                onClick = {
                    pressed = true
                    onClick()
                    pressed = false
                }
            )
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberAsyncImagePainter(character.image),
                contentDescription = character.name,
                modifier = Modifier.size(80.dp)
            )
            Spacer(Modifier.width(8.dp))
            Column {
                Text(character.name, style = MaterialTheme.typography.titleMedium)
                Text("Status: ${character.status}", style = MaterialTheme.typography.bodyMedium)
                Text("Species: ${character.species}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}