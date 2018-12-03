package uk.co.hughingram.retailapp.productdetail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.fragment_image.*
import uk.co.hughingram.retailapp.R
import uk.co.hughingram.retailapp.view.BaseFragment

class ImageFragment : BaseFragment() {

    override val fragmentLayout = R.layout.fragment_image
    override val isFullScreen = true

    final override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.move)   // TODO this should go onCreateView?
        postponeEnterTransition()
        return inflater.inflate(fragmentLayout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = ImageFragmentArgs.fromBundle(arguments).imageUrl

        image_fullscreen.transitionName = url

        postponeEnterTransition()

        Glide.with(context!!)
            .load(url)

            .listener(object : RequestListener<Drawable?> {
                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable?>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable?>?, isFirstResource: Boolean): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })

            .into(image_fullscreen)
        btn_close.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}