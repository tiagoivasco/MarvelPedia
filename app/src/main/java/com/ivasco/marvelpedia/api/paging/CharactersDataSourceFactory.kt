package com.ivasco.marvelpedia.api.paging

import androidx.paging.DataSource
import com.ivasco.marvelpedia.model.Character
import com.ivasco.marvelpedia.api.MarvelApi
import io.reactivex.disposables.CompositeDisposable

class CharactersDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val marvelAPI: MarvelApi
) : DataSource.Factory<Int, Character>() {
    override fun create(): DataSource<Int, Character> {
        return CharactersDataSource(marvelAPI, compositeDisposable)
    }
}