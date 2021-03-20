package com.hsmnzaydn.base.core_network

import com.google.gson.annotations.SerializedName

data class UploadImageResponseModel(
    @SerializedName("uploadPath") var path: String?
)
