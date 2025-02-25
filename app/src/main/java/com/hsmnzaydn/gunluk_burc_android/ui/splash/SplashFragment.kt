package com.hsmnzaydn.gunluk_burc_android.ui.splash

import com.hsmnzaydn.gunluk_burc_android.base.view.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hsmnzaydn.gunluk_burc_android.databinding.FragmentSplashBinding
import com.hsmnzaydn.gunluk_burc_android.ui.horoscopelist.HoroscopeListFragment
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashVM, FragmentSplashBinding>() {
    override fun getViewModelClass() = SplashVM::class.java
    override fun getViewBinding() = FragmentSplashBinding.inflate(layoutInflater)
    override fun initUI(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        Handler().postDelayed({
            mainNavigate(HoroscopeListFragment.newInstance())
        }, 2000)
    }


    override fun subscribeObservers() {
        super.subscribeObservers()

    }
    companion object {
        fun newInstance() = SplashFragment()
    }
}
