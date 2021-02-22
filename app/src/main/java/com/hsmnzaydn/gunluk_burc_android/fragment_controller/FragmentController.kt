package com.hsmnzaydn.gunluk_burc_android.fragment_controller

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hsmnzaydn.gunluk_burc_android.fragment_controller.animation.Animation
import com.hsmnzaydn.gunluk_burc_android.fragment_controller.custom.create
import com.hsmnzaydn.gunluk_burc_android.fragment_controller.data.Controller
import com.hsmnzaydn.gunluk_burc_android.fragment_controller.data.FragmentStack
import com.hsmnzaydn.gunluk_burc_android.fragment_controller.extension.getFragTag


/**
 * Created by Kemal Tunç on 2020-09-30
 */

class FragmentController(private val activity: Activity, private val containerId: Int) : Navigator {

    private var fragmentStack = ArrayList<FragmentStack>()
    private var controllerStack = ArrayList<Controller>()

    private var _backFragment: (tag: String, stack: FragmentStack) -> Unit = { _, _ -> }

    private var _onBackPressDetect: (detect: Boolean) -> Unit = { _ -> }


    var supportFragmentManager = (activity as AppCompatActivity).supportFragmentManager

    private var animation: Animation? = null

    private var fragmentBundle = Bundle()

    private var mainBundle: Bundle? = null

    override fun init(bundle: Bundle?) {

        this.mainBundle = bundle

        /*    val appCompatActivity = (activity as AppCompatActivity)
            appCompatActivity.onBackPressedDispatcher.addCallback(appCompatActivity, onBackListener)*/


        if (mainBundle != null) {
            val fragmentController = mainBundle?.getBundle(FRAGMENT_CONTROLLER)

            fragmentController?.let { data ->
                data.getParcelableArrayList<FragmentStack>(FRAGMENT_STACK)?.let {
                    fragmentStack.addAll(
                        it
                    )
                }

                data.getParcelableArrayList<Controller>(CONTROLLER_STACK)?.let {
                    controllerStack.addAll(
                        it
                    )
                }
            }
        }
    }


    override fun createChildContainer(
        containerId: Int
    ) {
        addController(containerId, CHILD_CONTROLLER)
    }

    override fun startFragment(fragment: Fragment, block: FragmentOption.Builder.() -> Unit) {
        addController(containerId, MAIN_CONTROLLER, FragmentOption.build(fragment, block))
    }


    private fun addController(
        containerId: Int,
        controllerName: String,
        fragmentOption: FragmentOption? = null
    ) {
        val find = controllerStack.any { it.controllerName == controllerName }

        if (!find) {
            controllerStack.add(Controller(controllerName, containerId))
            if (controllerName == MAIN_CONTROLLER && fragmentOption != null) {
                navigate(
                    controllerName,
                    fragmentOption
                )
            }
        }
    }


    override fun setAnimation(animation: Animation) {
        this.animation = animation
    }


    override fun mainNavigate(fragmentOption: FragmentOption) {
        navigate(MAIN_CONTROLLER, fragmentOption)
    }

    override fun childNavigate(fragmentOption: FragmentOption) {
        navigate(CHILD_CONTROLLER, fragmentOption)
    }

    override fun findController(controllerName: String, fragmentOption: FragmentOption) {
        navigate(controllerName, fragmentOption)
    }

    override fun navigateUp() {
        activity.onBackPressed()
    }

    override fun navigateUp(code: Int, intent: Intent) {
        currentFragment()?.let {
            it.targetFragment!!.onActivityResult(code, Activity.RESULT_OK, intent)
        }
        navigateUp()
    }

    private fun navigate(controllerName: String, fragmentOption: FragmentOption) {

        val controller = controllerStack.find { it.controllerName == controllerName }

        val fragmentManager = getFragmentManagerWithController(controllerName)

        if (controller != null && fragmentManager != null) {


            val fragTag = fragmentOption.fragment.getFragTag(fragmentOption.label)

            if (currentFragment() != null && currentFragment()?.tag ?: "" == fragTag) {
                Log.d(TAG, "SAME FRAGMENT")
            } else {

                val fragStackFirstIndex = fragmentStack.indexOfFirst { it.tag == fragTag }

                val condition =
                    fragStackFirstIndex != -1 &&
                            !fragmentOption.clearHistory &&
                            !fragmentStack[fragStackFirstIndex].firstTabFragment


                if (fragmentStack.isNotEmpty() && fragmentStack.last().tag == fragTag) {
                    val findFrag = findFragmentWithTag(fragTag, fragmentManager)
                    removeFragment(fragmentManager, findFrag, fragStackFirstIndex)
                } else if (fragStackFirstIndex != -1 && fragmentStack[fragStackFirstIndex].firstTabFragment && fragmentStack.count { it.tag == fragTag } == 2) {

                    val fragLastIndex = fragmentStack.indexOfLast { it.tag == fragTag }

                    val findFrag = findFragmentWithTag(fragTag, fragmentManager)

                    removeFragment(fragmentManager, findFrag, fragLastIndex)

                    fragmentStack[fragStackFirstIndex].firstTabFragment = true

                } else if (condition) {
                    val findFrag = findFragmentWithTag(fragTag, fragmentManager)
                    removeFragment(fragmentManager, findFrag, fragStackFirstIndex)
                }

                try {
                    val firstFrag = fragmentStack.indexOfFirst { it.firstTabFragment }

                    if (firstFrag != -1 && fragmentStack.count() > 1 && fragmentStack[firstFrag].tag == fragmentStack[firstFrag + 1].tag) {
                        fragmentStack.removeAt(firstFrag + 1)
                    }
                } catch (e: IndexOutOfBoundsException) {
                    e.printStackTrace()
                }


                if (fragmentOption.clearHistory) {
                    removeHistory()
                }

                val transaction = fragmentManager.beginTransaction()


                animation?.let {
                    transaction.setCustomAnimations(
                        it.enterAnimFromRight,
                        it.exitAnimToLeft,
                        it.enterAnimFromLeft,
                        it.exitAnimToRight
                    )
                }

                transaction.replace(
                    controller.containerId,
                    fragmentOption.fragment,
                    fragmentOption.fragment.getFragTag()
                )

                if (fragmentOption.history) {
                    transaction.addToBackStack(fragTag)
                    fragmentStack.add(
                        FragmentStack(
                            fragTag,
                            controllerName,
                            fragmentOption.tabMenuFragment,
                            getGroupId(fragmentOption),
                            fragmentOption.firstTabFragment,
                            fragmentOption.popupFragmentTag ?: ""
                        )
                    )
                }

                transaction.commitAllowingStateLoss()
            }

        } else {
            throw NullPointerException("Not found controller")
        }


    }

    private fun getGroupId(fragmentOption: FragmentOption) = when {
        fragmentOption.tabMenuFragment -> fragmentOption.groupId
        fragmentStack.size > 0 -> {
            val lastFrag = fragmentStack.last()
            lastFrag.groupId
        }
        else -> {
            0
        }
    }

    override fun getFirstFragmentTag(): String? {
        return fragmentStack.first().tag
    }

    private fun getBundle(): Bundle {
        val bundle = Bundle()

        bundle.putParcelableArrayList(FRAGMENT_STACK, fragmentStack)
        bundle.putParcelableArrayList(CONTROLLER_STACK, controllerStack)

        return bundle
    }

    override fun saveState(outState: Bundle) {
        outState.putBundle(FRAGMENT_CONTROLLER, getBundle())
    }

    private fun getFragmentManagerWithController(controllerName: String): FragmentManager? {
        return if (controllerName == MAIN_CONTROLLER) {
            return supportFragmentManager
        } else {
            findChildFragmentManager(supportFragmentManager)
        }
    }

    private fun findChildFragmentManager(supportFragmentManager: FragmentManager): FragmentManager? {
        return findFragmentWithFragmentManager(supportFragmentManager)?.childFragmentManager
    }

    private fun findFragmentWithFragmentManager(fragmentManager: FragmentManager): Fragment? {
        return fragmentManager.findFragmentById(containerId)
    }

    private fun findFragmentWithTag(tag: String, fragmentManager: FragmentManager?): Fragment? {
        return fragmentManager?.findFragmentByTag(tag)
    }

    private fun removeFragment(fragmentManager: FragmentManager, fragment: Fragment?, index: Int) {
        fragmentStack.removeAt(index)

        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit()
        }
    }

    private fun removeFragment(fragmentManager: FragmentManager, fragment: Fragment?) {
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit()
        }
    }


    private fun removeHistory() {
        fragmentStack.forEach { stack ->
            val fm = getFragmentManagerWithController(stack.controllerName)
            fm?.let {
                removeFragment(fm, findFragmentWithTag(stack.tag, it))
            }

        }
        fragmentBundle.clear()
        mainBundle?.clear()
        fragmentStack.clear()
    }

    override fun onBackPressed() {
        try {
            _onBackPressDetect(true)
            if (fragmentStack.size > 1) {
                val popupTag = fragmentStack.last().popupFragmentTag

                val currentFm =
                    getFragmentManagerWithController(fragmentStack.last().controllerName)

                var dest: FragmentStack

                if (popupTag.isNotEmpty()) {
                    dest = fragmentStack.find { it.tag == popupTag }
                        ?: throw NullPointerException("Not Found Popup fragment")

                    val popupFragmentIndex = fragmentStack.indexOfFirst { it.tag == popupTag }
                    val len = fragmentStack.size

                    if (len - 1 > popupFragmentIndex + 1) {
                        popupFragment(len, popupFragmentIndex, dest)
                        return
                    } else {
                        dest = fragmentStack.takeLast(2).first()
                        fragmentStack.remove(fragmentStack.last())
                    }
                } else {
                    dest = fragmentStack.takeLast(2).first()
                    fragmentStack.remove(fragmentStack.last())
                }

                val destFm = getFragmentManagerWithController(dest.controllerName)
                if (destFm == currentFm) {
                    destFm?.popBackStack(dest.tag, 0)
                    _backFragment(dest.tag, dest)
                } else {
                    currentFm?.popBackStack()
                }
            } else {
                activity.finish()
            }
        } catch (e: Exception) {
            Log.d(TAG, "Exception => " + e.message)
            activity.finish()
        }
    }

    private fun popupFragment(len: Int, popupFragmentIndex: Int, dest: FragmentStack) {
        for (i in len - 1 downTo popupFragmentIndex + 1) {
            val currentFm =
                getFragmentManagerWithController(fragmentStack.last().controllerName)
            fragmentStack.remove(fragmentStack.last())
            val destFm = getFragmentManagerWithController(dest.controllerName)
            if (destFm == currentFm) {
                destFm?.popBackStack(dest.tag, 0)
                _backFragment(dest.tag, dest)
            } else {
                currentFm?.popBackStack()
            }
        }
    }

    private fun currentFragment(): Fragment? {
        if (fragmentStack.size > 0) {
            val frag = fragmentStack.last()
            return findFragmentWithTag(
                frag.tag,
                getFragmentManagerWithController(frag.controllerName)
            )
        }
        return null
    }

    override fun onBackPressDetect(f: (detect: Boolean) -> Unit) {
        _onBackPressDetect = f
    }


    override fun backCurrentFragment(f: (tag: String, stack: FragmentStack) -> Unit) {
        _backFragment = f
    }


    override fun currentFragment(fragment: (fragment: Fragment) -> Unit) {
        currentFragment()?.let {
            fragment(it)
        }
    }

    override fun createBottomMenu(
        controllerName: String,
        view: BottomNavigationView,
        fragments: List<Fragment>
    ) {
        view.create(
            controllerName,
            fragments,
            this,
            createdNavMenu(fragmentBundle)
        )
        saveBottomNavMenu()
    }

    override fun saveBottomMenuState(outState: Bundle) {
        outState.putBoolean(CREATED_NAV_MENU, true)
    }

    override fun createdNavMenu(outState: Bundle?): Boolean {
        return outState?.getBoolean(CREATED_NAV_MENU, false) ?: false
    }

    private fun saveBottomNavMenu() {
        fragmentBundle.putBoolean(CREATED_NAV_MENU, true)
    }

    override fun getFragmentState(): Bundle {
        return fragmentBundle
    }


    companion object {
        const val TAG = "FragmentController"
        const val FRAGMENT_CONTROLLER = "fragment_controller"
        const val FRAGMENT_STACK = "fragment_stack"
        const val CONTROLLER_STACK = "controller_stack"
        const val CREATED_NAV_MENU = "created_nav_menu"

        const val MAIN_CONTROLLER = "main_controller"
        const val CHILD_CONTROLLER = "child_controller"
    }


}
