package com.mashjulal.android.imagegallery

import android.content.res.Resources
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import java.text.DecimalFormat

fun getScreenWidthInPixels() = Resources.getSystem().displayMetrics.widthPixels

fun getNumberStringRepresentation(number: Int) =
        (if (number >= 1000) DecimalFormat("#.#k").format(number.toFloat() / 1000)
        else number.toString())!!

@GlideModule
class MyAppGlideModule : AppGlideModule()