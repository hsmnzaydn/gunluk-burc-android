package com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.entities

import com.hsmnzaydn.gunluk_burc_android.utility.enum.RecyclerViewItemType
import com.hsmnzaydn.gunluk_burc_android.base.model.BaseEntity

data class Horoscope(
    val imagePath: String,
    val id: String,
    val title: String,
    val descriptions:List<Description>
)

data class HoroscopeListItem(
    val horoscope: Horoscope
) : BaseEntity(RecyclerViewItemType.HOROSCOPE.type)