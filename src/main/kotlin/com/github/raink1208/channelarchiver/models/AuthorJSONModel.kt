package com.github.raink1208.channelarchiver.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AuthorJSONModel{
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("username")
    @Expose
    var username: String? = null
}
