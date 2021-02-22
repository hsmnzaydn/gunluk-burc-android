package com.hsmnzaydn.gunluk_burc_android.base.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewbinding.ViewBinding
import com.hsmnzaydn.gunluk_burc_android.MainActivity
import com.hsmnzaydn.gunluk_burc_android.base.BaseViewModel
import com.hsmnzaydn.gunluk_burc_android.base.SharedViewModel
import com.hsmnzaydn.gunluk_burc_android.fragment_controller.FragmentController
import com.hsmnzaydn.gunluk_burc_android.fragment_controller.FragmentOption
import com.hsmnzaydn.gunluk_burc_android.utility.EventObserver


abstract class BaseFragment<ViewModel : BaseViewModel, T : ViewBinding> : Fragment(){

    private var baseActivity: BaseActivity? = null


    private lateinit var mainActivity: MainActivity
    lateinit var navigator: FragmentController

    private var _binding: T? = null

    val binding get() = _binding!!

    abstract fun getViewBinding(): T

    protected val viewModel: ViewModel by lazy { ViewModelProvider(this).get(getViewModelClass()) }
    protected abstract fun getViewModelClass(): Class<ViewModel>


    var fragVisible = true

    private var runOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding()
        initUI(inflater, container, savedInstanceState)

        if (!runOnce) {
            runOnce = true
            runOnce()
        } else {
            againOpened()
        }

        setOnClickListener()
        subscribeObservers()

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            this.baseActivity = context
            context.onFragmentAttached()
        }

        if (context is MainActivity) {
            mainActivity = context
            navigator = context.navigator
        }
    }

    open fun initUI(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {}

    open fun runOnce() {}

    open fun againOpened() {}

    fun mainNavigate(
        fragment: Fragment,
        block: FragmentOption.Builder.() -> Unit = {}
    ) {
        navigator.mainNavigate(FragmentOption.build(fragment, block))
    }

    fun childNavigate(
        fragment: Fragment,
        block: FragmentOption.Builder.() -> Unit = {}
    ) {
        navigator.childNavigate(FragmentOption.build(fragment, block))
    }

    fun navigateUp() {
        navigator.navigateUp()
    }



    open fun setOnClickListener() {}

    open fun subscribeObservers() {
        viewModel.errorHandle.observe(requireActivity(), EventObserver {


        })


        viewModel.showLoading.observe(requireActivity(), EventObserver {
            Toast.makeText(requireActivity(),"Loading",Toast.LENGTH_LONG).show()
        })


        viewModel.hideLoading.observe(requireActivity(), EventObserver {


        })

    }

    open fun delay(duration: Long, run: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            run()
        }, duration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    fun sharedViewModel(): SharedViewModel =
        ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)

    fun getMainActivity(): MainActivity? {
        return if (this::mainActivity.isInitialized) {
            mainActivity
        } else {
            null
        }
    }

    fun finish() {
        activity?.finish()
    }


    fun adjustFragment() {
        requireActivity().window
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    fun adjustPan() {
        getMainActivity()?.window
            ?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }


    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }
}
