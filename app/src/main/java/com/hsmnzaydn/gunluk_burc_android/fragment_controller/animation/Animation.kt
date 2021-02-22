package com.hsmnzaydn.gunluk_burc_android.fragment_controller.animation

import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import com.hsmnzaydn.gunluk_burc_android.R

/**
 * Created by Kemal Tunç on 2020-09-30
 */


class Animation(
    @AnimRes @AnimatorRes val enterAnimFromLeft: Int,
    @AnimRes @AnimatorRes val enterAnimFromRight: Int,
    @AnimRes @AnimatorRes val exitAnimToLeft: Int,
    @AnimRes @AnimatorRes val exitAnimToRight: Int
) {

    private constructor(builder: Builder) : this(
        builder.enterAnimFromLeft,
        builder.enterAnimFromRight,
        builder.exitAnimToLeft,
        builder.exitAnimToRight
    )

    companion object {
        inline fun build(
            block: Builder.() -> Unit
        ) = Builder(
        ).apply(block).build()
    }

    class Builder {

        @AnimRes
        @AnimatorRes
        val enterAnimFromLeft: Int = R.anim.enter_from_left
        @AnimRes
        @AnimatorRes
        val enterAnimFromRight: Int = R.anim.enter_from_right
        @AnimRes
        @AnimatorRes
        val exitAnimToLeft: Int = R.anim.exit_to_left
        @AnimRes
        @AnimatorRes
        val exitAnimToRight: Int = R.anim.exit_to_right


        fun build() = Animation(this)
    }
}