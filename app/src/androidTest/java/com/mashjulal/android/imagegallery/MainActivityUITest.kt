package com.mashjulal.android.imagegallery

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.mashjulal.android.imagegallery.activities.ImageActivity
import com.mashjulal.android.imagegallery.activities.MainActivity
import com.mashjulal.android.imagegallery.adapters.ImgurGalleryRecyclerViewAdapter
import com.mashjulal.android.imagegallery.adapters.ImgurImageRecyclerViewAdapter
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityUITest {

    @Rule
    @JvmField
    val activityMainRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Rule
    @JvmField
    val activityImageRule = ActivityTestRule<ImageActivity>(ImageActivity::class.java)

    @After
    fun tearDown() {
        activityMainRule.finishActivity()
    }

    @Test
    fun testImagesLoad() {
        onView(withId(R.id.rv_galleries))
                .check(ViewAssertions.matches(Matchers.recyclerViewSizeAbove(0)))
    }

    @Test
    fun testClickImage_OpensImageActivity() {
        Intents.init()

        onView(withId(R.id.rv_galleries))
                .perform(actionOnItemAtPosition<ImgurImageRecyclerViewAdapter.ViewHolder>(0, click()))
        intended(hasComponent(ImageActivity::class.java.name))
        assertTrue(activityImageRule.activity != null)
        activityImageRule.finishActivity()
        assertTrue(activityImageRule.activity == null)

        Intents.release()
    }

    @Test
    fun testScrollingToTop() {
        onView(withId(R.id.rv_galleries))
                .perform(scrollToPosition<ImgurGalleryRecyclerViewAdapter.ViewHolder>(5))
        onView(withId(R.id.fab_toTop))
                .check(matches(allOf( isEnabled(), isClickable())))
                .perform(ViewActions.buttonClick(), ViewActions.waitFor(2000))

        onView(withId(R.id.rv_galleries))
                .check(ViewAssertions.matches(Matchers.topChildHasPosition(0)))
    }

    @Test
    fun testAddingItemsOnScroll() {
        onView(withId(R.id.rv_galleries))
                .check(ViewAssertions.matches(Matchers.recyclerViewSizeEquals(10)))
                .perform(scrollToPosition<ImgurGalleryRecyclerViewAdapter.ViewHolder>(5))
                .check(ViewAssertions.matches(Matchers.recyclerViewSizeEquals(20)))
    }
}