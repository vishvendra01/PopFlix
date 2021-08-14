package com.app.newmoviesapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.newmoviesapp.data.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao
}