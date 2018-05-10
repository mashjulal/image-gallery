package com.mashjulal.android.imagegallery

import org.junit.Assert

class EpochTimeTest {

    fun testEpochToMillis() {
        val epoch = 100L

        val millisExpected = 100000
        val millisActual = epochToMillis(epoch)

        Assert.assertEquals(millisExpected, millisActual)
    }
}