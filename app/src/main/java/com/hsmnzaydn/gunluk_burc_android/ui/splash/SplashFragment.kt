package com.hsmnzaydn.gunluk_burc_android.ui.splash

import com.hsmnzaydn.gunluk_burc_android.base.view.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hsmnzaydn.gunluk_burc_android.databinding.FragmentSplashBinding
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

    }


    override fun subscribeObservers() {
        super.subscribeObservers()

    }
    companion object {
        fun newInstance() = SplashFragment()
    }
}
