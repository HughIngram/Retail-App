package uk.co.hughingram.retailapp

import android.support.test.InstrumentationRegistry
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.UiDevice
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.co.hughingram.retailapp.model.ApiClientProvider
import uk.co.hughingram.retailapp.model.Product
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
    fun productListShowsCorrectItems() {
        /** GIVEN - a list of products**/
        val products = randomProductList()
        /** WHEN - opening the app **/
        setUpMocksAndLaunch(products, loadSlowly = false)

        /** THEN - those same products are shown displayed, in same order **/
        products.forEachIndexed { index, product ->
            val listItemMatcher = nthChildOf(withId(R.id.product_recycler), index)
            val selectedProductName = getText(
                allOf(withParent(listItemMatcher), withId(R.id.name))
            )
            assertEquals(product.name, selectedProductName)
        }
    }

    private fun randomProductList(): List<Product> =
        listOf(generateRandomProduct(), generateRandomProduct(), generateRandomProduct())

    @Test
    fun rotatePhoneWhileLoading() {
        val products = randomProductList()
        /** WHEN - rotating the phone before the product list finishes loading **/
        val mockApiClient = setUpMocksAndLaunch(products, loadSlowly = true)
        val device = UiDevice.getInstance(getInstrumentation())
        device.setOrientationRight()
        mockApiClient.finishLoading = true
        /** THEN - the app should not crash! **/
    }

    private fun setUpMocksAndLaunch(products: List<Product>, loadSlowly: Boolean): MockApiClient {
        val application = InstrumentationRegistry.getTargetContext().applicationContext
        val mockApiClient = MockApiClient(products, loadSlowly = loadSlowly)
        (application as ApiClientProvider).apiClient = mockApiClient
        activityTestRule.launchActivity(null)
        return mockApiClient
    }
}

