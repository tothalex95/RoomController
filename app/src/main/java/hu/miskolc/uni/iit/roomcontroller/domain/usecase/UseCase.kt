package hu.miskolc.uni.iit.roomcontroller.domain.usecase

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class UseCase {

    protected var lastDisposable: Disposable? = null
    protected val compositeDisposable = CompositeDisposable()

    protected fun disposeLast() {
        lastDisposable?.run { if (!isDisposed) dispose() }
    }

    fun dispose() {
        compositeDisposable.clear()
    }

}
