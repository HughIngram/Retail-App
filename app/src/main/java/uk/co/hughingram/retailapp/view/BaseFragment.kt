package uk.co.hughingram.retailapp.view

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import uk.co.hughingram.retailapp.R

abstract class BaseFragment : Fragment() {

    @get:LayoutRes
    protected abstract val fragmentLayout: Int

    abstract val isFullScreen: Boolean

    @CallSuper
    override fun onResume() {
        super.onResume()
        val window = activity?.window!!
        // TODO remove this in case it interferes with shared element transition
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

//    final override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(R.transition.move)   // TODO this should go onCreateView?
//        return inflater.inflate(fragmentLayout, container, false)
//    }

}