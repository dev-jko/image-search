package com.nadarm.imagesearcher.di

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton


// TODO  AppSchedulers 클래스를 Singleton으로 쓰면 다 같은 스케쥴러를 쓰게 되나?
@Singleton
class AppSchedulers @Inject constructor() {
    fun ui(): Scheduler = AndroidSchedulers.mainThread()
    fun io(): Scheduler = Schedulers.io()
    fun computation(): Scheduler = Schedulers.computation()
}