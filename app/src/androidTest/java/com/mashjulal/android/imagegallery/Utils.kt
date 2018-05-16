package com.mashjulal.android.imagegallery

import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.matcher.ViewMatchers
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

object Matchers {

    fun recyclerViewSizeAbove(size: Int) = object: TypeSafeMatcher<View>() {
        override fun describeTo(description: Description?) {
            description!!.appendText("RecyclerView should have size > $size")
        }

        override fun matchesSafely(item: View?): Boolean {
            return (item as RecyclerView).adapter.itemCount > size
        }
    }

    fun recyclerViewSizeEquals(size: Int) = object: TypeSafeMatcher<View>() {
        override fun describeTo(description: Description?) {
            description!!.appendText("RecyclerView should have size = $size")
        }

        override fun matchesSafely(item: View?): Boolean {
            return (item as RecyclerView).adapter.itemCount == size
        }
    }

    fun topChildHasPosition(childPosition: Int) = object: TypeSafeMatcher<View>() {
        override fun describeTo(description: Description?) {
            description!!.appendText(
                    "RecyclerView Child View position must be equal to $childPosition.")
        }

        override fun matchesSafely(item: View?): Boolean {
            val layoutManager = (item as RecyclerView).layoutManager as LinearLayoutManager
            return layoutManager.findFirstVisibleItemPosition() == childPosition
        }
    }
}

object ViewActions {
    fun buttonClick() = object: ViewAction {
        override fun getDescription() = "Click button"

        override fun getConstraints(): Matcher<View> = ViewMatchers.isEnabled()

        override fun perform(uiController: UiController?, view: View?) {
            view!!.performClick()
        }
    }

    fun waitFor(millis: Long) = object: ViewAction {
        override fun getDescription() = "Click button"

        override fun getConstraints(): Matcher<View> = ViewMatchers.isEnabled()

        override fun perform(uiController: UiController, view: View) {
            uiController.loopMainThreadForAtLeast(millis)
        }
    }
}