package com.ivasco.marvelpedia.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail,
    val comics: ContentInfoList,
    val series: ContentInfoList,
    val events: ContentInfoList,
    val stories: ContentInfoList
) : Parcelable

@Parcelize
data class ContentInfoList(
    val available: Int,
    val collectionURI: String,
    val items: List<ContentInfoItem>
) : Parcelable

@Parcelize
data class ContentInfoItem(
    val resourceURI: String,
    val name: String
) : Parcelable

@Parcelize
data class Thumbnail(
    val path: String,
    val extension: String
) : Parcelable {
    fun getUrl(size: String): String {
        return "$path/$size.$extension"
    }

    companion object {
        const val STANDARD_MEDIUM = "standard_medium"
        const val LANDSCAPE_MEDIUM = "landscape_medium"
    }
}

@Parcelize
data class CharacterDataContainer(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<Character>
) : Parcelable

@Parcelize
data class CharacterDataWrapper(
    val code: Int,
    val etag: String,
    val data: CharacterDataContainer
) : Parcelable