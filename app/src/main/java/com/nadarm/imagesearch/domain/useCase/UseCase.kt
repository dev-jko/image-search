package com.nadarm.imagesearch.domain.useCase

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface SingleUseCase1<Param1, Type> {
    fun execute(param1: Param1): Single<Type>
}

interface SingleUseCase2<Param1, Param2, Type> {
    fun execute(param1: Param1, param2: Param2): Single<Type>
}

interface SingleUseCase4<Param1, Param2, Param3, Param4, Type> {
    fun execute(param1: Param1, param2: Param2, param3: Param3, param4: Param4): Single<Type>
}

interface CompletableUseCase1<Param1> {
    fun execute(param1: Param1): Completable
}

interface FlowableUseCase<Type> {
    fun execute(): Flowable<Type>
}