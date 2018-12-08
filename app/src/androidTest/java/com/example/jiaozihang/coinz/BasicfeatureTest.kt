package com.example.jiaozihang.coinz


import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class BasicfeatureTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val appCompatEditText = onView(
                allOf(withId(R.id.emailTxt),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()))
        appCompatEditText.perform(replaceText("s1622520@me.com"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
                allOf(withId(R.id.emailTxt), withText("s1622520@me.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()))
        appCompatEditText2.perform(pressImeActionButton())

        val appCompatEditText3 = onView(
                allOf(withId(R.id.passwordTxt),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        appCompatEditText3.perform(replaceText("123456"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
                allOf(withId(R.id.passwordTxt), withText("123456"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        appCompatEditText4.perform(pressImeActionButton())

        val appCompatButton = onView(
                allOf(withId(R.id.loginBtn), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()))
        appCompatButton.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatImageButton = onView(
                allOf(withId(R.id.image_moneybag),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()))
        appCompatImageButton.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        val appCompatImageButton2 = onView(
                allOf(withId(R.id.imageButtonTurtle),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()))
        appCompatImageButton2.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        val relativeLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV),
                        childAtPosition(
                                withClassName(`is`("android.widget.RelativeLayout")),
                                2)))
                .atPosition(6)
        relativeLayout.perform(click())

        val appCompatButton2 = onView(
                allOf(withId(R.id.to_bank), withText("Sent Bank"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()))
        appCompatButton2.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        val appCompatButton3 = onView(
                allOf(withId(R.id.send_gift), withText("Sent Gift"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()))
        appCompatButton3.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        val appCompatEditText5 = onView(
                allOf(withId(R.id.email_Txt),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()))
        appCompatEditText5.perform(click())

        val appCompatEditText6 = onView(
                allOf(withId(R.id.email_Txt),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()))
        appCompatEditText6.perform(replaceText("2802091242@qq.com"), closeSoftKeyboard())

        val appCompatEditText7 = onView(
                allOf(withId(R.id.email_Txt), withText("2802091242@qq.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()))
        appCompatEditText7.perform(pressImeActionButton())

        val appCompatButton4 = onView(
                allOf(withId(R.id.trading), withText("comfirm trade"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()))
        appCompatButton4.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(1000)

        pressBack()

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        pressBack()

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        val appCompatImageButton3 = onView(
                allOf(withId(R.id.image_bank),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("android.widget.LinearLayout")),
                                        1),
                                1),
                        isDisplayed()))
        appCompatImageButton3.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        pressBack()

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        val appCompatImageButton4 = onView(
                allOf(withId(R.id.image_bank),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("android.widget.LinearLayout")),
                                        1),
                                1),
                        isDisplayed()))
        appCompatImageButton4.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        pressBack()

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        val appCompatImageButton5 = onView(
                allOf(withId(R.id.image_moneybag),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()))
        appCompatImageButton5.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        val appCompatImageButton6 = onView(
                allOf(withId(R.id.imageButtonCrocodile),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        appCompatImageButton6.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        val relativeLayout2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV),
                        childAtPosition(
                                withClassName(`is`("android.widget.RelativeLayout")),
                                2)))
                .atPosition(6)
        relativeLayout2.perform(click())

        val relativeLayout3 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV),
                        childAtPosition(
                                withClassName(`is`("android.widget.RelativeLayout")),
                                2)))
                .atPosition(6)
        relativeLayout3.perform(click())

        val relativeLayout4 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV),
                        childAtPosition(
                                withClassName(`is`("android.widget.RelativeLayout")),
                                2)))
                .atPosition(6)
        relativeLayout4.perform(click())

        val relativeLayout5 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV),
                        childAtPosition(
                                withClassName(`is`("android.widget.RelativeLayout")),
                                2)))
                .atPosition(9)
        relativeLayout5.perform(click())

        val appCompatButton5 = onView(
                allOf(withId(R.id.to_bank), withText("Sent Bank"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()))
        appCompatButton5.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        pressBack()

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        pressBack()

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        val appCompatImageButton7 = onView(
                allOf(withId(R.id.image_bank),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("android.widget.LinearLayout")),
                                        1),
                                1),
                        isDisplayed()))
        appCompatImageButton7.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        pressBack()

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        val appCompatImageButton8 = onView(
                allOf(withId(R.id.image_logout),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        appCompatImageButton8.perform(click())
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
