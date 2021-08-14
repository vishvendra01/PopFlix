package com.app.newmoviesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.newmoviesapp.ui.listing.MovieListingFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        displayMovieListingFragment()
    }

    private fun displayMovieListingFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_fragment_container, MovieListingFragment())
            .commit()
    }
}