package com.mashjulal.android.imagegallery.api

enum class ImageThumbnail(val s: String) {
    SMALL_SQUARE("s"),
    BIG_SQUARE("b"),
    SMALL("t"),
    MEDIUM("m"),
    LARGE("l"),
    HUGE("h")
}

enum class ImageType(val value: String) {
    JPEG("image/jpeg"),
    GIF("image/gif")
}

fun getImageThumbnailLink(imageLink: String, thumbnail: ImageThumbnail): String {
    val pathWithoutExt = imageLink.substringBeforeLast(".")
    val ext = imageLink.substringAfterLast(".")
    return "%s%s.%s".format(pathWithoutExt, thumbnail.s, ext)
}

fun hasThumbnailSuffix(imageLink: String): Boolean {
    val fileName = imageLink.substringAfterLast("/")
    val imageIdWithSuffix = fileName.substringBeforeLast(".")
    val hasSuffix = ImageThumbnail.values().any { imageIdWithSuffix.last().toString() == it.s }
    return hasSuffix
}

fun removeThumbnailSuffix(imageLink: String): String {
    val fileName = imageLink.substringAfterLast("/")
    val imageIdWithSuffix = fileName.substringBeforeLast(".")
    val imageId =
            if (hasThumbnailSuffix(imageLink)) imageIdWithSuffix.substring(0, imageIdWithSuffix.length-1)
            else imageIdWithSuffix
    val path = imageLink.substringBeforeLast("/")
    val ext = fileName.substringAfterLast(".")
    return "%s/%s.%s".format(path, imageId, ext)
}