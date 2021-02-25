package com.hsmnzaydn.gunluk_burc_android.base.view

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity : AppCompatActivity(),
    BaseFragment.Callback {


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }



    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return false
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }



}