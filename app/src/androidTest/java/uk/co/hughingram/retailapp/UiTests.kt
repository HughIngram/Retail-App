package uk.co.hughingram.retailapp

import android.support.test.InstrumentationRegistry
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.UiDevice
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.co.hughingram.retailapp.model.ApiClientProvider
import uk.co.hughingram.retailapp.view.MainActivity

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class UiTests {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Test
    fun rotatePhoneWhileLoading() {
        val mockApiClient = setUpMocksAndLaunch(loadSlowly = true)
        val device = UiDevice.getInstance(getInstrumentation())
        device.setOrientationRight()
        mockApiClient.finishLoading = true
    }

    private fun setUpMocksAndLaunch(loadSlowly: Boolean): MockApiClient {
        val application = InstrumentationRegistry.getTargetContext().applicationContext
        val mockApiClient = MockApiClient(loadSlowly = loadSlowly)
        (application as ApiClientProvider).apiClient = mockApiClient
        activityTestRule.launchActivity(null)
        return mockApiClient
    }
}

fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> = object : TypeSafeMatcher<View>() {

    override fun describeTo(description: Description?) {
        description?.appendText("with $childPosition child of type parentMatcher")
    }

    override fun matchesSafely(item: View?): Boolean {
        if (item?.parent !is ViewGroup) {
            return parentMatcher.matches(item?.parent)
        }
        val group = item.parent as ViewGroup
        return parentMatcher.matches(item.parent) && group.getChildAt(childPosition) == item
    }
}

fun getText(matcher: Matcher<View>): String {

    var string: String? = null
    onView(matcher).perform(object : ViewAction {
        override fun getConstraints(): Matcher<View> = isAssignableFrom(TextView::class.java)

        override fun getDescription(): String = "getting text from a TextView"

        override fun perform(uiController: UiController, view: View) {
            val tv = view as TextView //Save, because of check in getConstraints()
            string = tv.text.toString()
        }
    })
    return string ?: ""
}
