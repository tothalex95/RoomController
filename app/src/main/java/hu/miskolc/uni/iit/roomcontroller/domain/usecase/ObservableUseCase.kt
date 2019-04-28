package hu.miskolc.uni.iit.roomcontroller.domain.usecase

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class ObservableUseCase<Type, in Params> : UseCase() {

    operator fun invoke(
        onSuccess: (type: Type) -> Unit,
        onError: (type: Throwable) -> Unit,
        onFinished: () -> Unit = {},
        params: Params
    ) {
        disposeLast()
        lastDisposable = execute(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate(onFinished)
            .subscribe(onSuccess, onError)
        lastDisposable?.run { compositeDisposable.add(this) }
    }

    internal abstract fun execute(params: Params): Observable<Type>

}
