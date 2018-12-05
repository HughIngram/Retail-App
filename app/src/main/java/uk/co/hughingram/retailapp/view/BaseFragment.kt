package uk.co.hughingram.retailapp.view

import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window

abstract class BaseFragment : Fragment() {

    @get:LayoutRes
    protected abstract val fragmentLayout: Int

    abstract val isFullScreen: Boolean

    @CallSuper
    override fun onResume() {
        super.onResume()
        val window = activity?.window!!
        if (isFullScreen) {
            (activity as AppCompatActivity).supportActionBar?.hide()
            hideSystemUI(window)
        } else {
            (activity as AppCompatActivity).supportActionBar?.show()
            showSystemUI(window)
        }
    }

    private fun hideSystemUI(window: Window) {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    private fun showSystemUI(window: Window) {
        window.decorView.systemUiVisibility = 0
    }

}