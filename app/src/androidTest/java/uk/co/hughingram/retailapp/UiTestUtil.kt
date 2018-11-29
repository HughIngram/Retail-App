package uk.co.hughingram.retailapp

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import uk.co.hughingram.retailapp.model.Product
import uk.co.hughingram.retailapp.model.ProductImage
import java.util.*
import kotlin.random.Random

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
