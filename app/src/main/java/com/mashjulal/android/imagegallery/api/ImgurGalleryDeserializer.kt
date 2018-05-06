package com.mashjulal.android.imagegallery.api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mashjulal.android.imagegallery.classes.ImgurGallery
import com.mashjulal.android.imagegallery.classes.ImgurImage
import com.mashjulal.android.imagegallery.epochToMillis
import java.lang.reflect.Type


/**
 * Json deserializer for Imgur Gallery objects.
 */
class ImgurGalleryDeserializer : JsonDeserializer<ImgurGallery> {

    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): ImgurGallery {
        val galleryObject = json.asJsonObject
        return deserializeGallery(galleryObject)
    }

    private fun deserializeGallery(galleryObject: JsonObject): ImgurGallery {
        val id = galleryObject.get("id").asString
        val title = galleryObject.get("title").asString
        val points = galleryObject.get("points").asInt
        val views = galleryObject.get("views").asInt
        val commentCount = galleryObject.get("comment_count").asInt
        val accountUrl = galleryObject.get("account_url").asString
        val datetime = epochToMillis(galleryObject.get("datetime").asLong)
        val link = galleryObject.get("link").asString.orEmpty()
        val images =
                if (galleryObject.has("images"))
                    galleryObject.getAsJsonArray("images")
                            .map { deserializeImage(it.asJsonObject) }
                else
                    listOf(deserializeImage(galleryObject))
        return ImgurGallery(id, title, points, views, commentCount, accountUrl, datetime, link, images)
    }

    private fun deserializeImage(imageObject: JsonObject): ImgurImage {
        val id = imageObject.get("id").asString
        val ttl = imageObject.get("title")
        val title = if (!ttl.isJsonNull) ttl.asString else ""
        val type = imageObject.get("type").asString
        val lnk = imageObject.get("link").asString
        val link = if (hasThumbnailSuffix(lnk, id)) removeThumbnailSuffix(lnk) else lnk
        val animated = imageObject.get("animated").asBoolean
        val width = imageObject.get("width").asInt
        val height = imageObject.get("height").asInt

        return ImgurImage(id, title, type, link, animated, width, height)
    }
}