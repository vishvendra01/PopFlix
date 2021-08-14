package com.app.newmoviesapp.data.repository

import com.app.newmoviesapp.data.entity.MovieEntity
import com.app.newmoviesapp.data.source.local.LocalSource
import com.app.newmoviesapp.data.source.remote.NetworkSource
import com.app.newmoviesapp.response.MovieListResponse
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

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
    fun `get movies have data in local source should emit data from local source`() {
        val sampleMovies = listOf<MovieEntity>(
            MovieEntity(0, "Title", "fjjf", "fjkdj", 0.5, "2014", 1),
            MovieEntity(1, "Title", "fjjf", "fjkdj", 0.5, "2014", 1),
            MovieEntity(2, "Title", "fjjf", "fjkdj", 0.5, "2014", 1)
        )

        Mockito.`when`(localSource.getMovies(anyInt())).thenReturn(Flowable.just(sampleMovies))
        Mockito.`when`(remoteSource.getMovies(anyInt())).thenReturn(
            Flowable.just(MovieListResponse(1, 1, 5, emptyList()))
        )


        val subscriber = TestSubscriber<List<MovieEntity>>()
        repository.getMovies(1).subscribe(subscriber)

        subscriber.assertComplete()
        subscriber.assertNoErrors()

    }
}