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
        @SerializedName("link") val link: String,
        @SerializedName("images") val images: List<ImgurImage>
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readLong(),
            parcel.readString(),
            parcel.createTypedArrayList(ImgurImage.CREATOR)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeInt(points)
        parcel.writeInt(views)
        parcel.writeInt(commentCount)
        parcel.writeString(accountUrl)
        parcel.writeLong(datetime)
        parcel.writeString(link)
        parcel.writeTypedList(images)
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