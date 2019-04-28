package hu.miskolc.uni.iit.roomcontroller.presentation.viewmodel

import androidx.lifecycle.ViewModel
import hu.miskolc.uni.iit.roomcontroller.domain.usecase.UseCase

abstract class BaseViewModel(
    vararg useCases: UseCase
) : ViewModel() {

    private val useCaseList = mutableListOf<UseCase>()

    init {
        useCaseList.addAll(useCases)
    }

    override fun onCleared() {
        super.onCleared()
        useCaseList.forEach { it.dispose() }
    }

}
