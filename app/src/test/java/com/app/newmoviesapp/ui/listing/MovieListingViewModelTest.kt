package com.app.newmoviesapp.ui.listing

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.app.newmoviesapp.data.entity.MovieEntity
import com.app.newmoviesapp.data.executor.SchedulerProviderImpl
import com.app.newmoviesapp.data.repository.MovieRepository
import com.app.newmoviesapp.ui.common.State
import io.reactivex.Flowable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.junit.MockitoJUnitRunner

class MovieListingViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var movieRepo: MovieRepository

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<State<List<MovieEntity>>>

    @Mock
    private lateinit var observer: Observer<State<List<MovieEntity>>>

    private lateinit var moviesViewModel: MovieListingViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        moviesViewModel = MovieListingViewModel(
            movieRepo,
            SchedulerProviderImpl()
        )
    }

    @Test
    fun `should set progress state in live data at first`() {
        val sampleMovies = listOf<MovieEntity>(
            MovieEntity(0, "Title", "fjjf", "fjkdj", "", 0.5, "2014", 1),
            MovieEntity(1, "Title", "fjjf", "fjkdj", "", 0.5, "2014", 1),
            MovieEntity(2, "Title", "fjjf", "fjkdj", "", 0.5, "2014", 1)
        )

        Mockito.`when`(movieRepo.getMovies(Mockito.anyInt())).thenReturn(
            Flowable.just(sampleMovies)
        )

        moviesViewModel.moviesLiveData.observeForever(observer)

        moviesViewModel.loadMovies(1)

        Mockito.verify(observer, Mockito.times(2)).onChanged(argumentCaptor.capture())
        val values = argumentCaptor.allValues
        Assert.assertTrue(values[0] is State.Loading)
    }

    @Test
    fun `should set emitted movies in live data from repository`() {
        val sampleMovies = listOf<MovieEntity>(
            MovieEntity(0, "Title", "fjjf", "fjkdj", "", 0.5, "2014", 1),
            MovieEntity(1, "Title", "fjjf", "fjkdj", "", 0.5, "2014", 1),
            MovieEntity(2, "Title", "fjjf", "fjkdj", "", 0.5, "2014", 1)
        )

        Mockito.`when`(movieRepo.getMovies(Mockito.anyInt())).thenReturn(
            Flowable.just(sampleMovies)
        )

        moviesViewModel.moviesLiveData.observeForever(observer)

        moviesViewModel.loadMovies(1)

        Mockito.verify(observer, Mockito.times(2)).onChanged(argumentCaptor.capture())
        val values = argumentCaptor.allValues
        Assert.assertTrue(values[1] is State.Success)

        val successState = values[1] as State.Success

        Assert.assertEquals(successState.data, sampleMovies)
    }

    @Test
    fun `when repo returns error then state should be error`() {
        Mockito.`when`(movieRepo.getMovies(Mockito.anyInt())).thenReturn(
            Flowable.error(Throwable("error occurred"))
        )

        moviesViewModel.moviesLiveData.observeForever(observer)

        moviesViewModel.loadMovies(1)

        Mockito.verify(observer, Mockito.times(2)).onChanged(argumentCaptor.capture())
        val values = argumentCaptor.allValues

        Assert.assertTrue(values[0] is State.Loading)

        Assert.assertTrue(values[1] is State.Error)
    }

}