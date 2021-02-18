package com.example.appcraft.ui

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.appcraft.R
import com.example.appcraft.databinding.FragmentPhotoPresenterBinding
import com.example.appcraft.utils.Constants.PHOTO_ID
import com.example.appcraft.utils.Constants.PHOTO_TITLE
import com.example.appcraft.utils.Constants.PHOTO_URL
import kotlinx.android.synthetic.main.fragment_photo_presenter.*

class PhotoPresenterFragment : Fragment(R.layout.fragment_photo_presenter) {

    private var _binding: FragmentPhotoPresenterBinding? = null
    private val binding: FragmentPhotoPresenterBinding get() = _binding!!
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val animation = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)

        sharedElementReturnTransition = animation
        sharedElementEnterTransition = animation

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPhotoPresenterBinding.bind(view)

        val photoUrl = arguments?.getString(PHOTO_URL,"null")
        val photoTitle = arguments?.getString(PHOTO_TITLE,"null")
        val photoId = arguments?.getLong(PHOTO_ID,-1)

        try{

            val url = GlideUrl(photoUrl, LazyHeaders.Builder().addHeader("User-Agent", "your-user-agent").build())

            Glide.with(requireContext())
                .load(url)
                .into(photo_presenter_activity_zoom_in_image_view)

        } catch (e: Exception) {

            photo_presenter_activity_zoom_in_image_view.setImageResource(R.drawable.placeholder_photo)

        }

    }


}