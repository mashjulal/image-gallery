package com.mashjulal.android.imagegallery.api

/**
 * Imgur possible image thumbnail size enumeration.
 */
enum class ImageThumbnail(val s: String) {
    SMALL_SQUARE("s"),
    BIG_SQUARE("b"),
    SMALL("t"),
    MEDIUM("m"),
    LARGE("l"),
    HUGE("h")
}

/**
 *  Media MIME type enumeration from Imgur.
 */
enum class MediaType(val value: String) {
    JPEG("image/jpeg"),
    GIF("image/gif"),
    MP4("video/mp4")
}

/**
 * Returns link to image thumbnail.
 * @param imageLink source image link
 * @param thumbnail image thumbnail type from [ImageThumbnail]
 * @return image thumbnail link
 */
fun getImageThumbnailLink(imageLink: String, thumbnail: ImageThumbnail): String {
    val pathWithoutExt = imageLink.substringBeforeLast(".")
    val ext = imageLink.substringAfterLast(".")
    return "%s%s.%s".format(pathWithoutExt, thumbnail.s, ext)
}

/**
 * Returns if image link is thumbnail link.
 * @param imageLink selected image link
 * @param imageId selected image id
 * @return link has thumbnail suffix or not
 */
fun hasThumbnailSuffix(imageLink: String, imageId: String): Boolean {
    val fileName = imageLink.substringAfterLast("/")
    val imageIdWithSuffix = fileName.substringBeforeLast(".")
    return imageIdWithSuffix != imageId
}

/**
 * Returns source image link
 * @param imageLink link to thumbnail image
 * @return source image link
 */
fun removeThumbnailSuffix(imageLink: String): String {
    val fileName = imageLink.substringAfterLast("/")
    val imageIdWithSuffix = fileName.substringBeforeLast(".")
    val imageId = imageIdWithSuffix.substring(0, imageIdWithSuffix.length-1)
    val path = imageLink.substringBeforeLast("/")
    val ext = fileName.substringAfterLast(".")
    return "%s/%s.%s".format(path, imageId, ext)
}