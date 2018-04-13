package com.mashjulal.android.imagegallery

import com.mashjulal.android.imagegallery.api.removeThumbnailSuffix
import org.junit.Assert.assertEquals
import org.junit.Test

class ThumbnailTest {

    @Test
    fun testRemoveThumbnail() {
        val url = "http://i.imgur.com/JmHLXXvh.gif"

        val expectedUrl = "http://i.imgur.com/JmHLXXv.gif"
        val actualUrl = removeThumbnailSuffix(url)

        assertEquals(expectedUrl, actualUrl)
    }

}