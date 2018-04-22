package com.mashjulal.android.imagegallery

import com.mashjulal.android.imagegallery.api.ImageThumbnail
import com.mashjulal.android.imagegallery.api.getImageThumbnailLink
import com.mashjulal.android.imagegallery.api.hasThumbnailSuffix
import com.mashjulal.android.imagegallery.api.removeThumbnailSuffix
import org.junit.Assert.*
import org.junit.Test

class ThumbnailTest {

    @Test
    fun testRemoveThumbnail_WithThumbnailSymbol() {
        val url = "http://i.imgur.com/JmHLXXvh.gif"

        val expectedUrl = "http://i.imgur.com/JmHLXXv.gif"
        val actualUrl = removeThumbnailSuffix(url)

        assertEquals(expectedUrl, actualUrl)
    }

    @Test
    fun testHasThumbnail_True() {
        val url = "http://i.imgur.com/JmHLXXvh.gif"
        val id = "JmHLXXv"

        val hasSuffix = hasThumbnailSuffix(url, id)

        assertTrue(hasSuffix)
    }

    @Test
    fun testHasThumbnail_False() {
        val url = "http://i.imgur.com/JmHLXXv.gif"
        val id = "JmHLXXv"

        val hasSuffix = hasThumbnailSuffix(url, id)

        assertFalse(hasSuffix)
    }

    @Test
    fun testGetImageThumbnailLink_SmallSquare() {
        val url = "http://i.imgur.com/JmHLXXv.gif"

        val expectedUrl = "http://i.imgur.com/JmHLXXvs.gif"
        val actualUrl = getImageThumbnailLink(url, ImageThumbnail.SMALL_SQUARE)

        assertEquals(expectedUrl, actualUrl)
    }

    @Test
    fun testGetImageThumbnailLink_BigSquare() {
        val url = "http://i.imgur.com/JmHLXXv.gif"

        val expectedUrl = "http://i.imgur.com/JmHLXXvb.gif"
        val actualUrl = getImageThumbnailLink(url, ImageThumbnail.BIG_SQUARE)

        assertEquals(expectedUrl, actualUrl)
    }

    @Test
    fun testGetImageThumbnailLink_Small() {
        val url = "http://i.imgur.com/JmHLXXv.gif"

        val expectedUrl = "http://i.imgur.com/JmHLXXvt.gif"
        val actualUrl = getImageThumbnailLink(url, ImageThumbnail.SMALL)

        assertEquals(expectedUrl, actualUrl)
    }

    @Test
    fun testGetImageThumbnailLink_Medium() {
        val url = "http://i.imgur.com/JmHLXXv.gif"

        val expectedUrl = "http://i.imgur.com/JmHLXXvm.gif"
        val actualUrl = getImageThumbnailLink(url, ImageThumbnail.MEDIUM)

        assertEquals(expectedUrl, actualUrl)
    }

    @Test
    fun testGetImageThumbnailLink_Large() {
        val url = "http://i.imgur.com/JmHLXXv.gif"

        val expectedUrl = "http://i.imgur.com/JmHLXXvl.gif"
        val actualUrl = getImageThumbnailLink(url, ImageThumbnail.LARGE)

        assertEquals(expectedUrl, actualUrl)
    }

    @Test
    fun testGetImageThumbnailLink_Huge() {
        val url = "http://i.imgur.com/JmHLXXv.gif"

        val expectedUrl = "http://i.imgur.com/JmHLXXvh.gif"
        val actualUrl = getImageThumbnailLink(url, ImageThumbnail.HUGE)

        assertEquals(expectedUrl, actualUrl)
    }
}