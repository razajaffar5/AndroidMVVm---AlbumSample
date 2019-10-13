package com.raza.albumviewer.binding

import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.raza.albumviewer.data.Image
import com.raza.albumviewer.util.prefs.KEY_EXTRA_LARGE
import com.raza.albumviewer.util.prefs.KEY_LARGE
import com.raza.albumviewer.util.prefs.KEY_MEDIUM
import com.raza.albumviewer.util.prefs.KEY_SMALL

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, images: List<Image>?) {
    images?.let {
        val imageItem = images.first { it.size == KEY_MEDIUM }
        val url = imageItem.text
        url?.let {
                Glide.with(view.context)
                    .load(url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(view)
        }
    }
}


@BindingAdapter("coverFromUrl")
fun bindCoverPhotoFromUrl(view: ImageView, images: List<Image>?) {
    images?.let {
        val imageItem = images.first { it.size == KEY_EXTRA_LARGE }
        val url = imageItem.text
        url?.let {
            Glide.with(view.context)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        }
    }
}

