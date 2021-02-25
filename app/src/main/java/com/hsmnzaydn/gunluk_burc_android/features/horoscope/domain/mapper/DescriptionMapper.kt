package com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.mapper

import com.hsmnzaydn.gunluk_burc_android.features.horoscope.data.entities.DescriptionResponse
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.entities.Description
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.entities.DescriptionListItem

fun DescriptionResponse.toDescription(): Description = Description(
    title,
    description
)

fun Description.toDescriptionItem():DescriptionListItem = DescriptionListItem(
    this
)