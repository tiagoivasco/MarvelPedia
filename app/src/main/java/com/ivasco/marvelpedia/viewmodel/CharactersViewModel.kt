package com.ivasco.marvelpedia.viewmodel

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.ivasco.marvelpedia.api.MarvelApi
import com.ivasco.marvelpedia.api.paging.CharactersDataSourceFactory
import com.ivasco.marvelpedia.model.Character

class CharactersViewModel : ViewModel() {
    var characterList: Observable<PagedList<Character>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 20

    private var sourceFactory: CharactersDataSourceFactory =
        CharactersDataSourceFactory(compositeDisposable, MarvelApi.getService())

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build()

        characterList = RxPagedListBuilder(sourceFactory, config)
            .setFetchScheduler(Schedulers.io())
            .buildObservable()
            .cache()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}