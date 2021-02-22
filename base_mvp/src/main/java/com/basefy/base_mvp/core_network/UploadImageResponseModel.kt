package com.basefy.base_mvp.core_network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UploadImageResponseModel(
    @SerializedName("uploadPath") var path: String?
)
