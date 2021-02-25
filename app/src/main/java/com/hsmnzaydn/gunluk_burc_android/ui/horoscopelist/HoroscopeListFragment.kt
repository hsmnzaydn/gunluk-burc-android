package com.hsmnzaydn.gunluk_burc_android.ui.horoscopelist

import com.hsmnzaydn.gunluk_burc_android.base.view.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.basefy.base_mvp.core_utility.onInitGrid
import com.basefy.base_mvp.core_utility.vertical
import com.hsmnzaydn.gunluk_burc_android.base.model.BaseEntity
import com.hsmnzaydn.gunluk_burc_android.databinding.FragmentHoroscopeListBinding
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.entities.HoroscopeListItem
import com.hsmnzaydn.gunluk_burc_android.ui.adapter.HoroscopeAdapter
import com.hsmnzaydn.gunluk_burc_android.ui.horoscopedetail.HoroscopeDetailFragment

@AndroidEntryPoint
class HoroscopeListFragment : BaseFragment<HoroscopeListVM, FragmentHoroscopeListBinding>() {
    override fun getViewModelClass() = HoroscopeListVM::class.java
    override fun getViewBinding() = FragmentHoroscopeListBinding.inflate(layoutInflater)

    val horoscopeAdapter by lazy {
        HoroscopeAdapter()
    }

    override fun initUI(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        horoscopeAdapter.onInitGrid(binding.recyclerView,column = 3).onItemClick { baseEntity, positionItem, layoutId ->
            when(layoutId){
                HoroscopeAdapter.HoroscopeViewHolder.ITEM_TYPE -> {
                    mainNavigate(HoroscopeDetailFragment.newInstance((baseEntity as HoroscopeListItem).horoscope.id))
                }
            }
        }
    }

    private val horoscopeList = Observer<List<BaseEntity>> {
        if (it.isEmpty()) {
        } else {
            horoscopeAdapter.items = it
        }
    }

    override fun subscribeObservers() {
        super.subscribeObservers()
        viewModel.horoscopeList.observe(viewLifecycleOwner,horoscopeList)
    }

    override fun runOnce() {
        super.runOnce()
        viewModel.getHoroscopes(System.currentTimeMillis())
    }

    companion object {
        fun newInstance() = HoroscopeListFragment()
    }
}
