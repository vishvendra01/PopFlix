package com.arch.mvi

import io.reactivex.Observable
import io.reactivex.Observable.error
import io.reactivex.Observable.merge
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

abstract class AbstractActionProcessor<A : MviAction, R : MviResult> : ObservableTransformer<A, R> {

    protected abstract val actionSubProcessors: List<AbstractActionSubProcessor<*>>

    final override fun apply(upstream: Observable<A>): ObservableSource<R> {
        return upstream.publish { actions ->
            val invalidActionSubProcessor = actions.filter { action ->
                actionSubProcessors.map { it.actionClass }
                    .none { it.isAssignableFrom(action.javaClass) }
            }.flatMap { unhandledAction ->
                error<R> {
                    IllegalArgumentException(
                        "Unknown action ${unhandledAction.javaClass.name}"
                    )
                }
            }

            merge(actionSubProcessors.map { subProc -> actions.compose(subProc) })
                .mergeWith(invalidActionSubProcessor)
        }
    }

    protected abstract inner class AbstractActionSubProcessor<T : MviAction> :
        ObservableTransformer<A, R> {

        abstract val actionClass: Class<out T>

        final override fun apply(upstream: Observable<A>): Observable<R> {
            return upstream.ofType(actionClass).switchMap {
                process(actionClass.cast(it)!!)
            }
        }

        abstract fun process(action: T): ObservableSource<R>
    }
}