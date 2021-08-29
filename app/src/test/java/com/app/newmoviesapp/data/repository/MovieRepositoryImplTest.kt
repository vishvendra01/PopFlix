package com.app.newmoviesapp.data.repository

import com.app.newmoviesapp.data.entity.MovieEntity
import com.app.newmoviesapp.data.mapper.toMovieEntities
import com.app.newmoviesapp.data.model.Movie
import com.app.newmoviesapp.data.source.local.LocalSource
import com.app.newmoviesapp.data.source.remote.NetworkSource
import com.app.newmoviesapp.response.MovieListResponse
import io.reactivex.Flowable
import io.reactivex.observers.TestObserver
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*

class MovieRepositoryImplTest {
    lateinit var repository: MovieRepository

    @Mock
    lateinit var localSource: LocalSource

    @Mock
    lateinit var remoteSource: NetworkSource

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        repository = MovieRepositoryImpl(remoteSource, localSource)
    }

    @Test
    fun `should emit cached results first when cached data is available`() {
        val sampleMovies = listOf<MovieEntity>(
            MovieEntity(0, "Title", "fjjf", "fjkdj", "", 0.5, "2014", 1),
            MovieEntity(1, "Title", "fjjf", "fjkdj", "", 0.5, "2014", 1),
            MovieEntity(2, "Title", "fjjf", "fjkdj", "", 0.5, "2014", 1)
        )

        Mockito.`when`(localSource.getMovies(anyInt())).thenReturn(Flowable.just(sampleMovies))
        Mockito.`when`(remoteSource.getMovies(anyInt())).thenReturn(
            Flowable.just(MovieListResponse(1, 1, 5, emptyList()))
        )

        val subscriber = repository.getMovies(1).test()
        subscriber.assertValueAt(0, sampleMovies)

        subscriber.assertComplete()
        subscriber.assertNoErrors()

    }

    @Test
    fun `should emit remote results when cache result are not available`() {
        val cachedMovies = emptyList<MovieEntity>()
        val sampleMovies = listOf<Movie>(
            Movie(0, "Title", "fjjf", "fjkdj", "", 0.5, "2014")
        )
        val verifyMovies = sampleMovies.toMovieEntities()

        Mockito.`when`(localSource.getMovies(anyInt()))
            .thenReturn(Flowable.just(cachedMovies), Flowable.just(verifyMovies))
        Mockito.`when`(remoteSource.getMovies(anyInt())).thenReturn(
            Flowable.just(MovieListResponse(1, 1, 5, sampleMovies))
        )

        val subscriber = repository.getMovies(1).test()
        subscriber.assertValueAt(0, verifyMovies)
        subscriber.assertValueCount(1)

        subscriber.assertComplete()
        subscriber.assertNoErrors()

        Mockito.verify(localSource, Mockito.times(2)).getMovies(Mockito.anyInt())

    }

    @Test
    fun `show emit cached results first then remote results and insert remote results into database`() {
        val cachedMovies = listOf<MovieEntity>(
            MovieEntity(0, "SpiderMan", "www.google.com", "www.google.com", "lorem ipsum", 0.5, "2014", 1),
        )
        val remoteMovies = listOf<Movie>(
            Movie(0, "Avengers", "fjjf", "fjkdj", "", 0.5, "2014")
        )
        val remoteMovieEntities = remoteMovies.toMovieEntities()

        Mockito.`when`(localSource.getMovies(anyInt()))
            .thenReturn(Flowable.just(cachedMovies), Flowable.just(remoteMovieEntities))
        Mockito.`when`(remoteSource.getMovies(anyInt())).thenReturn(
            Flowable.just(MovieListResponse(1, 1, 1, remoteMovies))
        )

        val subscriber = repository.getMovies(1).test()
        subscriber.assertValueAt(0, cachedMovies)
        subscriber.assertValueAt(1, remoteMovieEntities)
        subscriber.assertValueCount(2)
        subscriber.assertComplete()
        subscriber.assertNoErrors()

        Mockito.verify(localSource, Mockito.times(2)).getMovies(Mockito.anyInt())
        Mockito.verify(localSource).insertMovies(Mockito.anyList())
    }

    @Test
    fun `in case of network error show emit cache results then emit error`() {
        val cachedMovies = listOf<MovieEntity>(
            MovieEntity(0, "SpiderMan", "www.google.com", "www.google.com", "lorem ipsum", 0.5, "2014", 1),
        )

        val errorMessage = "something went wrong"

        Mockito.`when`(localSource.getMovies(anyInt()))
            .thenReturn(Flowable.just(cachedMovies))
        Mockito.`when`(remoteSource.getMovies(anyInt())).thenReturn(
            Flowable.error(Throwable(errorMessage))
        )

        val subscriber = repository.getMovies(1).test()
        subscriber.assertValueAt(0, cachedMovies)
        subscriber.assertValueCount(1)
        subscriber.assertErrorMessage(errorMessage)

        Mockito.verify(localSource).getMovies(Mockito.anyInt())
        Mockito.verify(localSource, Mockito.never()).insertMovies(Mockito.anyList())
    }
}