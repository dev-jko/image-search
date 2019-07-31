package com.nadarm.imagesearch.data.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ImageSearchResponse {
    @SerializedName("meta")
    @Expose
    var meta: Meta? = null

    @SerializedName("documents")
    @Expose
    var documents: List<Document>? = null
}


class Meta {
    @SerializedName("total_count")
    @Expose
    var totalCount: Int? = null

    @SerializedName("pageable_count")
    @Expose
    var pageableCount: Int? = null

    @SerializedName("is_end")
    @Expose
    var isEnd: Boolean? = null
}

class Document {
    @SerializedName("collection")
    @Expose
    var collection: String? = null

    @SerializedName("thumbnail_url")
    @Expose
    var thumbnailUrl: String? = null

    @SerializedName("image_url")
    @Expose
    var imageUrl: String? = null

    @SerializedName("width")
    @Expose
    var width: Int? = null

    @SerializedName("height")
    @Expose
    var height: Int? = null

    @SerializedName("display_sitename")
    @Expose
    var displaySitename: String? = null

    @SerializedName("doc_url")
    @Expose
    var docUrl: String? = null

    @SerializedName("datetime")
    @Expose
    var datetime: String? = null
}