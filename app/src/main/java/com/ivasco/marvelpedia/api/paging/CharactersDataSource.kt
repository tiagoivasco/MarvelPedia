package com.ivasco.marvelpedia.api.paging

import androidx.paging.PageKeyedDataSource
import com.ivasco.marvelpedia.api.MarvelApi
import io.reactivex.disposables.CompositeDisposable
import com.ivasco.marvelpedia.model.Character

class CharactersDataSource(
    private val marvelAPI: MarvelApi,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Character>() {

    companion object {
        var search: String? = null
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Character>
    ) {
        val numberOfItems = params.requestedLoadSize
        createObservable(0, 1, numberOfItems, callback, null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page + 1, numberOfItems, null, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page - 1, numberOfItems, null, callback)
    }

    private fun createObservable(
        requestedPage: Int,
        adjacentPage: Int,
        requestedLoadSize: Int,
        initialCallBack: LoadInitialCallback<Int, Character>?,
        callBack: LoadCallback<Int, Character>?
    ) {
        compositeDisposable.add(
            marvelAPI.getCharacters(requestedPage * requestedLoadSize, search)
                .subscribe { response ->
                    initialCallBack?.onResult(response.data.results, null, adjacentPage)
                    callBack?.onResult(response.data.results, adjacentPage)
                }
        )
    }
}