package com.mashjulal.android.imagegallery.classes

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Parcelable POJO class of Imgur image item.
 */
data class ImgurImage(
        @SerializedName("id") val id: String,
        @SerializedName("title") private val title: String,
        @SerializedName("type") val type: String,
        @SerializedName("link") val link: String,
        @SerializedName("animated") val animated: Boolean,
        @SerializedName("width") val width: Int,
        @SerializedName("height") val height: Int
) : Parcelable {

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
        parcel.writeString(title)
        parcel.writeString(type)
        parcel.writeString(link)
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