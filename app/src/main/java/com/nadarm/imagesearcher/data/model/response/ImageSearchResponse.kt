package com.nadarm.imagesearcher.data.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class ImageSearchResponse(
    @SerializedName("meta")
    @Expose
    val meta: Meta,

    @SerializedName("documents")
    @Expose
    val documents: List<Document>
)


data class Meta(
    @SerializedName("total_count")
    @Expose
    var totalCount: Int,

    @SerializedName("pageable_count")
    @Expose
    var pageableCount: Int,

    @SerializedName("is_end")
    @Expose
    var isEnd: Boolean
)

data class Document(
    @SerializedName("collection")
    @Expose
    var collection: String,

    @SerializedName("thumbnail_url")
    @Expose
    var thumbnailUrl: String,

    @SerializedName("image_url")
    @Expose
    var imageUrl: String,

    @SerializedName("width")
    @Expose
    var width: Int,

    @SerializedName("height")
    @Expose
    var height: Int,

    @SerializedName("display_sitename")
    @Expose
    var displaySiteName: String,

    @SerializedName("doc_url")
    @Expose
    var docUrl: String,

    @SerializedName("datetime")
    @Expose
    var datetime: String
)