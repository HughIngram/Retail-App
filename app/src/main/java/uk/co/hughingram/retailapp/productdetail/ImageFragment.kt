package uk.co.hughingram.retailapp.productdetail

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_image.*
import uk.co.hughingram.retailapp.R
import uk.co.hughingram.retailapp.view.BaseFragment

class ImageFragment : BaseFragment() {

    override val fragmentLayout = R.layout.fragment_image

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = ImageFragmentArgs.fromBundle(arguments).imageUrl
        Glide.with(context!!)
            .load(url)
            .into(image_fullscreen)
    }
}