package com.example.appcraft.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.appcraft.R
import com.example.appcraft.adapters.PhotosAdapterDb
import com.example.appcraft.databinding.FragmentAlbumDetailsDbBinding
import com.example.appcraft.db.AlbumsViewModel
import com.example.appcraft.db.photos.PhotoEntity
import com.example.appcraft.utils.Constants.ALBUM_ID_DATA
import com.example.appcraft.utils.Constants.ALBUM_TITLE_DATA
import com.example.appcraft.utils.Constants.ALBUM_USER_ID
import com.example.appcraft.utils.Constants.PHOTO_ID
import com.example.appcraft.utils.Constants.PHOTO_TITLE
import com.example.appcraft.utils.Constants.PHOTO_URL
import com.example.appcraft.utils.SharedPreferenceUtil
import kotlinx.android.synthetic.main.fragment_album_details_db.*

class AlbumDetailsDbFragment : Fragment(R.layout.fragment_album_details_db), PhotosAdapterDb.OnItemClickListener  {

    private val TAG = "AlbumDetailsDbFragment"

    private var _binding: FragmentAlbumDetailsDbBinding? = null
    private val binding: FragmentAlbumDetailsDbBinding get() = _binding!!

    private lateinit var albumsViewModel: AlbumsViewModel
    private lateinit var photosAdapterDb: PhotosAdapterDb

    private var mAlbumId = -1L
    private var mAlbumTitle = "null"
    private var mAlbumUserId = -1L


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAlbumDetailsDbBinding.bind(view)

        photosAdapterDb = PhotosAdapterDb(this, requireContext())


        // Inflate the layout for this fragment
        if(arguments != null) {
            mAlbumId = arguments?.getLong(ALBUM_ID_DATA,-1)!!
            mAlbumTitle = arguments?.getString(ALBUM_TITLE_DATA,"null")!!
            mAlbumUserId = arguments?.getLong(ALBUM_USER_ID,-1L)!!
            requireActivity().actionBar?.title = mAlbumTitle

            SharedPreferenceUtil.saveAlbumIdPref(requireContext(),mAlbumId)

        }


        albumsViewModel = ViewModelProvider(this).get(AlbumsViewModel::class.java)
        albumsViewModel.allPhotos.observe(requireActivity(), Observer { words ->
            words?.let {
                photosAdapterDb.setItems(it)
                photosAdapterDb.notifyDataSetChanged()
            }
        })

        // Handling RecycleView and it's animation
        val lac: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_fall_down)

        album_details_db_fragment_recycler_view.adapter = photosAdapterDb
        album_details_db_fragment_recycler_view.layoutAnimation = lac
        album_details_db_fragment_recycler_view.startLayoutAnimation()



        album_details_db_fragment_delete_album.setOnClickListener{

            albumsViewModel.deleteAlbumById(mAlbumId)

            for (i in photosAdapterDb.getItems().indices) {

                albumsViewModel.deletePhoto(photosAdapterDb.getItems()[i])

            }

            requireActivity().onBackPressed()
            Toast.makeText(context, "Album deleted...", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "setOnClickListener: deleted data")
        }

    }

    override fun onItemClick(v: View?, position: Int) {
        Log.d(TAG, "onItemClick: clicked")
    }

    override fun onPhotoClick(photoItem: PhotoEntity, position: Int, imageView: ImageView) {
        Log.d(TAG, "onPhotoClick: opening photo fragment")

        val bundle = Bundle()
        bundle.putString(PHOTO_URL,photoItem.url)
        bundle.putString(PHOTO_TITLE,photoItem.photoTitle)
        bundle.putLong(PHOTO_ID,photoItem.photoId)

        val extras = FragmentNavigatorExtras(imageView to "big_photo_transition")
        findNavController().navigate(R.id.action_albumDetailsDbFragment_to_photoPresenterFragment,
            bundle,
            null,
            extras)
    }

}