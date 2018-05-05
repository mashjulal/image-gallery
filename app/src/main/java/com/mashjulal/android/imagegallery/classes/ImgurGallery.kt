package com.mashjulal.android.imagegallery.classes

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Parcelable POJO class of Imgur gallery item.
 */
data class ImgurGallery(
        @SerializedName("id")val id: String,
        @SerializedName("title") val title: String,
        @SerializedName("points") val points: Int,
        @SerializedName("views") val views: Int,
        @SerializedName("comment_count") val commentCount: Int,
        @SerializedName("account_url") val accountUrl: String,
        @SerializedName("datetime") val datetime: Long,
        @SerializedName("images") private val _images: List<ImgurImage>?,
        @SerializedName("type") private val _type: String?,
        @SerializedName("width") private val _width: Int?,
        @SerializedName("height") private val _height: Int?,
        @SerializedName("link") private val _link: String?,
        @SerializedName("animated") private val _animated: Boolean?
) : Parcelable {
    val link: String
        get() = _link ?: ""
    val type: String
        get() = _type ?: ""
    val animated: Boolean
        get() = _animated ?: false
    val width: Int
        get() = _width ?: 0
    val height: Int
        get() = _height ?: 0
    val images: List<ImgurImage>
        get() = _images ?: ArrayList()

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readLong(),
            parcel.createTypedArrayList(ImgurImage),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean)

    constructor(id: String, title: String, points: Int, views: Int,
                commentCount: Int, accountUrl: String, datetime: Long, images: List<ImgurImage>?)
            : this(id, title, points, views, commentCount, accountUrl, datetime, images, null, null, null, null, null)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeInt(points)
        parcel.writeInt(views)
        parcel.writeInt(commentCount)
        parcel.writeString(accountUrl)
        parcel.writeLong(datetime)
        parcel.writeTypedList(_images)
        parcel.writeString(_type)
        parcel.writeValue(_width)
        parcel.writeValue(_height)
        parcel.writeString(_link)
        parcel.writeValue(_animated)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImgurGallery> {
        override fun createFromParcel(parcel: Parcel): ImgurGallery {
            return ImgurGallery(parcel)
        }

        override fun newArray(size: Int): Array<ImgurGallery?> {
            return arrayOfNulls(size)
        }
    }

}