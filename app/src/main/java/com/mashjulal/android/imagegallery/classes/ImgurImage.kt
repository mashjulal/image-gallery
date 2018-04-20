package com.mashjulal.android.imagegallery.classes

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.mashjulal.android.imagegallery.api.hasThumbnailSuffix
import com.mashjulal.android.imagegallery.api.removeThumbnailSuffix

data class ImgurImage(
        @SerializedName("id") val id: String,
        @SerializedName("title") private val _title: String?,
        @SerializedName("type") val type: String,
        @SerializedName("link") private val _link: String,
        @SerializedName("animated") val animated: Boolean,
        @SerializedName("width") val width: Int,
        @SerializedName("height") val height: Int
) : Parcelable {
    val link: String
        get() = if (hasThumbnailSuffix(_link, id)) removeThumbnailSuffix(_link) else _link
    val title: String
        get() = _title ?: ""

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readInt(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(_title)
        parcel.writeString(type)
        parcel.writeString(_link)
        parcel.writeByte(if (animated) 1 else 0)
        parcel.writeInt(width)
        parcel.writeInt(height)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImgurImage> {
        override fun createFromParcel(parcel: Parcel): ImgurImage {
            return ImgurImage(parcel)
        }

        override fun newArray(size: Int): Array<ImgurImage?> {
            return arrayOfNulls(size)
        }
    }
}