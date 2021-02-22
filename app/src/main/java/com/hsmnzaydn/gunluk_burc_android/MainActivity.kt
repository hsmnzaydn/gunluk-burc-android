package com.hsmnzaydn.gunluk_burc_android

import android.os.Bundle
import androidx.lifecycle.Observer
import com.hsmnzaydn.gunluk_burc_android.base.view.BaseActivity
import com.hsmnzaydn.gunluk_burc_android.databinding.ActivityMainBinding
import com.hsmnzaydn.gunluk_burc_android.fragment_controller.FragmentController
import com.hsmnzaydn.gunluk_burc_android.fragment_controller.animation.Animation
import com.hsmnzaydn.gunluk_burc_android.ui.splash.SplashFragment
import com.hsmnzaydn.gunluk_burc_android.utility.NetworkConnectionListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity() {


    private lateinit var binding: ActivityMainBinding
    val navigator = FragmentController(this, R.id.rootView)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigator.apply {
            init(null)
            setAnimation(Animation.build { })
            startFragment(SplashFragment.newInstance()) {
                history = false
            }
        }

        networkConnectionListener()

    }

    override fun onBackPressed() {
        navigator.onBackPressed()
    }


    private fun networkConnectionListener() {
        val connectionLiveData = NetworkConnectionListener(this)
        connectionLiveData.observe(this, Observer { isConnected ->
            isConnected?.let { isConnected ->
                if (!isConnected) {

                }
            }
        })
    }




}