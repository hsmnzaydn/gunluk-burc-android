package com.hsmnzaydn.gunluk_burc_android.fragment_controller.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Kemal Tunç on 2020-09-30
 */


@Parcelize
data class FragmentStack(
    var tag: String,
    var controllerName: String,
    var tabMenuFragment: Boolean = false,
    var groupId: Int = 0,
    var firstTabFragment: Boolean = false,
    var popupFragmentTag: String = ""
) : Parcelable
