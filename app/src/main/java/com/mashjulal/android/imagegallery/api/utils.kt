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
    val lastDotIndex = imageLink.lastIndexOf(".")
    val path = imageLink.subSequence(0, lastDotIndex) as String
    val ext = imageLink.subSequence(lastDotIndex + 1, imageLink.length)
    return path + thumbnail.s + "." + ext
}