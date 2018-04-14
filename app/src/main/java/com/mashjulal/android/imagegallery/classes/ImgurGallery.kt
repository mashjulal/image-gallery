package com.mashjulal.android.imagegallery.classes

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ImgurGallery(
        @SerializedName("id")val id: String,
        @SerializedName("title") val title: String,
        @SerializedName("score") val score: Int,
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

    constructor(id: String, title: String, score: Int, images: List<ImgurImage>?)
            : this(id, title, score, images, null, null, null, null, null)

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.createTypedArrayList(ImgurImage),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeInt(score)
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