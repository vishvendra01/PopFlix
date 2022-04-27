package com.app.newmoviesapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.newmoviesapp.movieslist.ui.MovieListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        displayMovieListingFragment()
    }

    private fun displayMovieListingFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_fragment_container, MovieListFragment())
            .commit()
    }
}