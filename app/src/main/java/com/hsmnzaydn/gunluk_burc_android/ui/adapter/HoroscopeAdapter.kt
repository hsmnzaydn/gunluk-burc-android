package com.hsmnzaydn.gunluk_burc_android.ui.adapter

import android.view.ViewGroup
import com.hsmnzaydn.base.core_utility.CoreImageloaderUtility
import com.hsmnzaydn.gunluk_burc_android.R
import com.hsmnzaydn.gunluk_burc_android.base.adapter.BaseAdapter
import com.hsmnzaydn.gunluk_burc_android.base.adapter.BaseViewHolder
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.entities.HoroscopeListItem
import com.hsmnzaydn.gunluk_burc_android.utility.enum.RecyclerViewItemType
import com.hsmnzaydn.gunluk_burc_android.base.model.BaseEntity
import com.hsmnzaydn.gunluk_burc_android.databinding.CellHoroscopeBinding
import com.hsmnzaydn.gunluk_burc_android.databinding.RowDescriptionBinding
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.entities.DescriptionListItem

class HoroscopeAdapter : BaseAdapter<BaseEntity>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BaseEntity> {
        return when(viewType){
            HoroscopeViewHolder.ITEM_TYPE -> {
                HoroscopeViewHolder(parent)
            }
            HoroscopeDescriptionViewHolder.ITEM_TYPE -> {
                HoroscopeDescriptionViewHolder(parent)
            }
            else -> HoroscopeViewHolder(parent)

        } as BaseViewHolder<BaseEntity>
    }


    class HoroscopeViewHolder(parent: ViewGroup) :
        BaseViewHolder<HoroscopeListItem>(parent, R.layout.cell_horoscope) {

        override val binding = CellHoroscopeBinding.bind(itemView)


        override fun bindItem(item: HoroscopeListItem) {
            with(binding) {
                CoreImageloaderUtility.imageLoaderWithCacheFit(context,item.horoscope.imagePath,backgroundimageview)
                titleTextView.text = item.horoscope.title
            }
        }

        companion object {
            val ITEM_TYPE = RecyclerViewItemType.HOROSCOPE.type
        }
    }

    class HoroscopeDescriptionViewHolder(parent: ViewGroup) :
        BaseViewHolder<DescriptionListItem>(parent, R.layout.row_description) {

        override val binding = RowDescriptionBinding.bind(itemView)


        override fun bindItem(item: DescriptionListItem) {
            with(binding) {
                title.text = item.description.title
                description.text = item.description.description
            }
        }

        companion object {
            val ITEM_TYPE = RecyclerViewItemType.DESCRIPTION.type
        }
    }

}