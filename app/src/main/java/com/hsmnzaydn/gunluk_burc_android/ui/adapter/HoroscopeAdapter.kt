package com.hsmnzaydn.gunluk_burc_android.ui.adapter

import android.view.ViewGroup
import com.basefy.base_mvp.core_utility.CoreImageloaderUtility
import com.hsmnzaydn.gunluk_burc_android.R
import com.hsmnzaydn.gunluk_burc_android.base.adapter.BaseAdapter
import com.hsmnzaydn.gunluk_burc_android.base.adapter.BaseViewHolder
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.entities.HoroscopeListItem
import com.hsmnzaydn.gunluk_burc_android.utility.enum.RecyclerViewItemType
import com.hsmnzaydn.gunluk_burc_android.base.model.BaseEntity
import com.hsmnzaydn.gunluk_burc_android.databinding.CellHoroscopeBinding

class HoroscopeAdapter : BaseAdapter<BaseEntity>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BaseEntity> {
        return HoroscopeViewHolder(parent) as BaseViewHolder<BaseEntity>
    }


    class HoroscopeViewHolder(parent: ViewGroup) :
        BaseViewHolder<HoroscopeListItem>(parent, R.layout.cell_horoscope) {

        override val binding = CellHoroscopeBinding.bind(itemView)


        override fun bindItem(item: HoroscopeListItem) {
            with(binding) {
                CoreImageloaderUtility.imageLoaderWithCacheFit(context,item.imagePath,backgroundimageview)
                titleTextView.text = item.title
            }
        }

        companion object {
            val ITEM_TYPE = RecyclerViewItemType.HOROSCOPE.type
        }
    }

}