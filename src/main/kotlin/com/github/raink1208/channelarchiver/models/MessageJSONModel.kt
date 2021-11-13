package com.github.raink1208.channelarchiver.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MessageJSONModel {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("content")
    @Expose
    var content: String? = null

    @SerializedName("channel_id")
    @Expose
    var channelId: String? = null

    @SerializedName("author")
    @Expose
    var author: AuthorJSONModel? = null
}