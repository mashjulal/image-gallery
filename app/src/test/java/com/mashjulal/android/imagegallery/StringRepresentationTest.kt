package com.mashjulal.android.imagegallery

import org.junit.Assert
import org.junit.Test
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.Instant
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

class DateStringRepresentationTest {

    @Test
    fun testDateStringRepresentation_now() {
        val dateMillis = Instant.now().toEpochMilli()

        val dateStringExpected = SimpleDateFormat("dd.MM.yy HH:mm").format(dateMillis)
        val dateStringActual = getDateRepresentation(dateMillis)

        Assert.assertEquals(dateStringExpected, dateStringActual)
    }

    @Test
    fun testDateStringRepresentation_fixed() {
        val millis = 1200000L

        val dateStringExpected = "01.01.70 3:20"
        val dateStringActual = getDateRepresentation(millis)

        Assert.assertEquals(dateStringExpected, dateStringActual)
    }
}