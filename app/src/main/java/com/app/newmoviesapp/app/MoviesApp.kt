package com.app.newmoviesapp.app

import android.app.Application
import com.app.newmoviesapp.di.app.AppComponent
import com.app.newmoviesapp.di.app.DaggerAppComponent

class MoviesApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().context(this).build()
    }
}