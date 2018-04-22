package com.mashjulal.android.imagegallery

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Environment
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.mashjulal.android.imagegallery.classes.ImgurImage
import java.text.DecimalFormat

fun getScreenWidthInPixels() = Resources.getSystem().displayMetrics.widthPixels

fun getNumberStringRepresentation(number: Int) =
        (if (number >= 1000) DecimalFormat("#.#k").format(number.toFloat() / 1000)
        else number.toString())!!

fun openInBrowser(context: Context, link: String) {
    val address = Uri.parse(link)
    val intent = Intent(Intent.ACTION_VIEW, address)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
}

fun download(context: Context, image: ImgurImage) {
    val address = Uri.parse(image.link)
    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val request = DownloadManager.Request(address)
    request
            .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    address.path.substringAfterLast("/"))
            .setVisibleInDownloadsUi(true)
            .setAllowedNetworkTypes(
                    DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setMimeType(image.type)
            .setAllowedOverRoaming(true)
    request.allowScanningByMediaScanner()
    downloadManager.enqueue(request)
}

@GlideModule
class MyAppGlideModule : AppGlideModule()