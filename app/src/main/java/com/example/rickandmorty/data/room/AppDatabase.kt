package com.example.rickandmorty.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickandmorty.data.api.FavoriteCharacterDao
import com.example.rickandmorty.data.model.FavoriteCharacter

@Database(
    entities = [FavoriteCharacter::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteCharacterDao(): FavoriteCharacterDao
}