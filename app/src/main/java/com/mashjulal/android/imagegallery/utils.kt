package com.mashjulal.android.imagegallery

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.Uri
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.mashjulal.android.imagegallery.classes.ImgurImage
import java.text.DateFormat
import java.text.DecimalFormat

/**
 * Returns device screen width in pixels.
 * @return device screen width in pixels
 */
fun getScreenWidthInPixels() = Resources.getSystem().displayMetrics.widthPixels

/**
 * Formats number to "x.xk" form.
 * @param number original number
 * @return formatted number string
 */
fun getNumberStringRepresentation(number: Int) =
        (if (number >= 1000) DecimalFormat("#.#k").format(number.toFloat() / 1000)
        else number.toString())!!

/**
 * Formats epoch seconds to millis.
 * @param epochTime Epoch time in seconds
 * @return epoch time in milliseconds
 */
fun epochToMillis(epochTime: Long) = epochTime * 1000

/**
 * Formats time millis to specified date format.
 * @param time time in milliseconds
 * @return formatted date
 */
fun getDateRepresentation(time: Long) =
        DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(time)!!

/**
 * Opens link in browser.
 * @param context activity context
 * @param link link to site
 */
fun openInBrowser(context: Context, link: String) {
    val address = Uri.parse(link)
    val intent = Intent(Intent.ACTION_VIEW, address)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
}

/**
 * Downloads selected image from Imgur.
 * @param context application context
 * @param image Imgur image
 */
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

/**
 * Checks if external storage is available for writing.
 * @return is external storage writable
 */
fun isExternalStorageWritable(): Boolean {
    return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
}

/**
 * Checks if application has specified permission.
 * @param context application context
 * @param permission permission string
 * @return application has permission or not
 */
fun hasPermission(context: Context, permission: String) =
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

/**
 * Requests specified permission.
 * @param activity activity for what permission is requesting
 * @param permission permission string
 * @param requestCode permission request code
 */
fun requestPermission(activity: Activity, permission: String, requestCode: Int) {
    ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
}

@GlideModule
class MyAppGlideModule : AppGlideModule()