package com.hsmnzaydn.gunluk_burc_android.ui.horoscopedetail

import com.hsmnzaydn.gunluk_burc_android.base.view.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.hsmnzaydn.base.core_utility.CoreImageloaderUtility
import com.hsmnzaydn.base.core_utility.vertical
import com.hsmnzaydn.gunluk_burc_android.base.model.BaseEntity
import com.hsmnzaydn.gunluk_burc_android.databinding.FragmentHoroscopeDetailBinding
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.entities.Horoscope
import com.hsmnzaydn.gunluk_burc_android.ui.adapter.HoroscopeAdapter
import com.hsmnzaydn.gunluk_burc_android.utility.BundleConstant

@AndroidEntryPoint
class HoroscopeDetailFragment : BaseFragment<HoroscopeDetailVM, FragmentHoroscopeDetailBinding>() {
    override fun getViewModelClass() = HoroscopeDetailVM::class.java
    override fun getViewBinding() = FragmentHoroscopeDetailBinding.inflate(layoutInflater)

    val horoscopeAdapter by lazy {
        HoroscopeAdapter()
    }

    override fun initUI(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {

        horoscopeAdapter.vertical(binding.recyclerView)
    }

    private val descriptionList = Observer<List<BaseEntity>> {
        horoscopeAdapter.items = it
    }

    private val horoscope = Observer<Horoscope>{
        CoreImageloaderUtility.imageLoaderWithCacheFit(requireActivity(),it.imagePath,binding.imageview)
    }

    override fun subscribeObservers() {
        super.subscribeObservers()
        viewModel.horoscope.observe(viewLifecycleOwner,horoscope)
        viewModel.descriptionList.observe(viewLifecycleOwner,descriptionList)
    }

    override fun runOnce() {
        super.runOnce()
        viewModel.getHoroscopeDetail(arguments?.getString(BundleConstant.HOROSCOPE_ID)?:"")
    }

    companion object {
        fun newInstance(horoscopeId:String) =   HoroscopeDetailFragment().apply {
            arguments = Bundle().apply {
                putString(BundleConstant.HOROSCOPE_ID,horoscopeId)
            }
        }
    }
}
