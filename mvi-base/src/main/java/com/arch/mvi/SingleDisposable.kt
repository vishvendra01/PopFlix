package com.arch.mvi

import io.reactivex.disposables.Disposable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SingleDisposable : ReadWriteProperty<Any, Disposable?> {
    private var s: Disposable? = null

    override fun getValue(thisRef: Any, property: KProperty<*>) = s

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Disposable?) {
        s?.dispose()
        s = value
    }
}