package com.app.newmoviesapp.app

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.arch.mvi.MviIntent
import com.arch.mvi.ViewState
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

abstract class BaseFragment<I : MviIntent, S : ViewState, VM : ViewModel> : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    abstract val clazz: Class<VM>

    protected val _intents: PublishSubject<I> = PublishSubject.create()
    val intents: Observable<I> = _intents.hide()

    val compositeBag = CompositeDisposable()

    val vm: VM by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(clazz)
    }

    override fun onDestroy() {
        _intents.onComplete()
        super.onDestroy()
    }

    abstract fun intents(): Observable<I>

    abstract fun render(state: S)
}