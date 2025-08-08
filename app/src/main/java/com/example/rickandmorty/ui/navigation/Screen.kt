package com.example.rickandmorty.ui.navigation

sealed class Screen(val route: String) {
    object Characters : Screen("characters")
    object CharacterDetail : Screen("character_detail/{characterId}") {
        fun createRoute(id: Int) = "character_detail/$id"
    }

    object Episodes : Screen("episodes")
    object EpisodeDetail : Screen("episode_detail/{episodeId}") {
        fun createRoute(id: Int) = "episode_detail/$id"
    }

    object Locations : Screen("locations")
    object LocationDetail : Screen("location_detail/{locationId}") {
        fun createRoute(id: Int) = "location_detail/$id"
    }

    object Favorites : Screen("favorites")
}