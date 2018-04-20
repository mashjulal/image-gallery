package com.mashjulal.android.imagegallery

import org.junit.Assert
import org.junit.Test
import java.text.DecimalFormat
import java.util.*

class NumberStringRepresentationTest {

    @Test
    fun testNumberRepresentation_randomUnder1000() {
        val low = 0
        val high = 999
        val num = Random().nextInt(high - low) + low

        val expectedRep = num.toString()
        val actualRep = getNumberStringRepresentation(num)

        Assert.assertEquals(expectedRep, actualRep)
    }

    @Test
    fun testNumberRepresentation_randomAbove1000() {
        val low = 1000
        val high = 999999
        val num = Random().nextInt(high - low) + low

        val expectedRep = DecimalFormat("#.#k").format(num.toFloat() / 1000)
        val actualRep = getNumberStringRepresentation(num)

        Assert.assertEquals(expectedRep, actualRep)
    }
}