package com.app.newmoviesapp.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.newmoviesapp.data.entity.MovieEntity
import io.reactivex.Flowable

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Query("Select * from movie where page=:page")
    fun getMovies(page : Int): Flowable<List<MovieEntity>>
}