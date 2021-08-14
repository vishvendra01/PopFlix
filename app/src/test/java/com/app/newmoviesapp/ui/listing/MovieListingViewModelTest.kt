package com.app.newmoviesapp.ui.listing

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.newmoviesapp.data.entity.MovieEntity
import com.app.newmoviesapp.data.executor.SchedulerProviderImpl
import com.app.newmoviesapp.data.repository.MovieRepository
import com.app.newmoviesapp.ui.common.State
import io.reactivex.Flowable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MovieListingViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var movieRepo: MovieRepository

    private lateinit var moviesViewModel: MovieListingViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        moviesViewModel = MovieListingViewModel(
            movieRepo,
            SchedulerProviderImpl()
        )
    }

    @Test
    fun `testmovie load`() {
        val sampleMovies = listOf<MovieEntity>(
            MovieEntity(0, "Title", "fjjf", "fjkdj", 0.5, "2014", 1),
            MovieEntity(1, "Title", "fjjf", "fjkdj", 0.5, "2014", 1),
            MovieEntity(2, "Title", "fjjf", "fjkdj", 0.5, "2014", 1)
        )

        Mockito.`when`(movieRepo.getMovies(Mockito.anyInt())).thenReturn(
            Flowable.just(sampleMovies)
        )
        moviesViewModel.loadMovies(1)

        Assert.assertTrue(moviesViewModel.moviesLiveData.value is State.Success)
        val state = moviesViewModel.moviesLiveData.value
        if(state is State.Success){
            Assert.assertTrue(state.data.size == 3)
        }
    }

}