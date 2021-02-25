package com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.entities

import com.hsmnzaydn.gunluk_burc_android.base.model.BaseEntity
import com.hsmnzaydn.gunluk_burc_android.utility.enum.RecyclerViewItemType

data class Description(
    var title:String,
    var description:String
)

data class DescriptionListItem(
    val description: Description
) : BaseEntity(RecyclerViewItemType.DESCRIPTION.type)