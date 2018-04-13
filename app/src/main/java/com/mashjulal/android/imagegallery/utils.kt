package com.mashjulal.android.imagegallery

import android.content.res.Resources
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

fun getScreenWidthInPixels() = Resources.getSystem().displayMetrics.widthPixels

@GlideModule
class MyAppGlideModule : AppGlideModule()