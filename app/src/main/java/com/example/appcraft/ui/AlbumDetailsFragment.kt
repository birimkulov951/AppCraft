package com.example.appcraft.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.appcraft.R
import com.example.appcraft.adapters.PhotosAdapter
import com.example.appcraft.databinding.FragmentAlbumDetailsBinding
import com.example.appcraft.db.AlbumsViewModel
import com.example.appcraft.db.albums.AlbumEntity
import com.example.appcraft.db.photos.PhotoEntity
import com.example.appcraft.model.photos.PhotosListItem
import com.example.appcraft.retrofit.ApiService
import com.example.appcraft.retrofit.RetrofitInstance
import com.example.appcraft.utils.Constants.ALBUM_ID_DATA
import com.example.appcraft.utils.Constants.ALBUM_TITLE_DATA
import com.example.appcraft.utils.Constants.ALBUM_USER_ID
import com.example.appcraft.utils.Constants.PHOTO_ID
import com.example.appcraft.utils.Constants.PHOTO_TITLE
import com.example.appcraft.utils.Constants.PHOTO_URL
import kotlinx.android.synthetic.main.fragment_album_details.*
import kotlinx.android.synthetic.main.fragment_albums_db.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumDetailsFragment : Fragment(R.layout.fragment_album_details), PhotosAdapter.OnItemClickListener {

    private val TAG = "AlbumDetailsFragment"

    private var _binding: FragmentAlbumDetailsBinding? = null
    private val binding: FragmentAlbumDetailsBinding get() = _binding!!

    private lateinit var albumsViewModel: AlbumsViewModel
    private lateinit var photosAdapter: PhotosAdapter

    private var mAlbumId = -1L
    private var mAlbumTitle = "null"
    private var mAlbumUserId = -1L

    private var mPhotosList = emptyList<PhotosListItem>()
    private var isAlbumAlreadySaved = false



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAlbumDetailsBinding.bind(view)

        // Inflate the layout for this fragment
        if(arguments != null) {
            mAlbumId = arguments?.getLong(ALBUM_ID_DATA,-1)!!
            mAlbumTitle = arguments?.getString(ALBUM_TITLE_DATA,"null")!!
            mAlbumUserId = arguments?.getLong(ALBUM_USER_ID,-1L)!!
            requireActivity().actionBar?.title = mAlbumTitle
        }


        albumsViewModel = ViewModelProvider(this).get(AlbumsViewModel::class.java)
        albumsViewModel.allAlbums.observe(requireActivity(), Observer { albums ->
            albums?.let {

                for (i in albums.indices) {
                    if (albumsViewModel.allAlbums.value!![i].albumId == mAlbumId) {
                        isAlbumAlreadySaved = true
                    }
                }

            }
        })



        // Load all album photos
        getPhotosList()


        album_details_fragment_save_album.setOnClickListener{

            if (isAlbumAlreadySaved) {
                Toast.makeText(context, "Album is already saved to database...", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Album saved...", Toast.LENGTH_SHORT).show()
                isAlbumAlreadySaved = true

                val album = AlbumEntity(mAlbumId,mAlbumTitle,mAlbumUserId)

                albumsViewModel.insertAlbum(album)

                for (i in mPhotosList.indices) {
                    val photo = PhotoEntity(mPhotosList[i].albumId,
                            mPhotosList[i].id,
                            mPhotosList[i].title,
                            mPhotosList[i].url,
                            mPhotosList[i].thumbnailUrl
                    )
                    albumsViewModel.insertPhoto(photo)

                }
                Log.e(TAG, "setOnClickListener: inserted data")
            }


        }

    }


    private fun getPhotosList() {

        album_details_fragment_save_album.visibility = View.GONE

        val retroInstance = RetrofitInstance.getRetrofitInstance().create(ApiService::class.java)
        val call = retroInstance.getPhotosListByAlbumId(mAlbumId)

        call.enqueue(object : Callback<MutableList<PhotosListItem>> {
            override fun onResponse(call: Call<MutableList<PhotosListItem>>, response: Response<MutableList<PhotosListItem>>) {

                album_details_fragment_progress_bar.visibility = View.GONE
                album_details_fragment_save_album.visibility = View.VISIBLE

                if (response.isSuccessful) {

                    mPhotosList = response.body() as MutableList<PhotosListItem>

                    photosAdapter = PhotosAdapter(this@AlbumDetailsFragment, requireContext())
                    photosAdapter.setItems(mPhotosList)

                    // Handling RecycleView and it's animation
                    val lac: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_fall_down)

                    album_details_fragment_recycler_view.adapter = photosAdapter
                    album_details_fragment_recycler_view.layoutAnimation = lac
                    album_details_fragment_recycler_view.startLayoutAnimation()

                } else {
                    Toast.makeText(requireContext(), "Unknown error", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<MutableList<PhotosListItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: $t")
                Toast.makeText(requireContext(), "Unknown error", Toast.LENGTH_SHORT).show()
                album_details_fragment_progress_bar.visibility = View.GONE
            }

        })

    }

    override fun onItemClick(v: View?, position: Int) {
        // no need for onItemCLickListener
        Log.d(TAG, "onItemClick: clicked")
    }

    override fun onPhotoClick(photoItem: PhotosListItem, position: Int, imageView: ImageView) {
        Log.d(TAG, "onPhotoClick: opening photo fragment")

        val bundle = Bundle()
        bundle.putString(PHOTO_URL,photoItem.url)
        bundle.putString(PHOTO_TITLE,photoItem.title)
        bundle.putLong(PHOTO_ID,photoItem.id)

        val extras = FragmentNavigatorExtras(imageView to "big_photo_transition")
        findNavController().navigate(R.id.action_albumDetailsFragment_to_photoPresenterFragment,
        bundle,
        null,
        extras)


     /*   val intent = Intent(requireActivity(), PhotoPresenterFragment::class.java)
        intent.putExtra("EXTRA_PHOTO_ITEM", photoItem.thumbnailUrl)
        intent.putExtra("EXTRA_PHOTO_TRANSITION_NAME", ViewCompat.getTransitionName(imageView))

        val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(),
            imageView,
            ViewCompat.getTransitionName(imageView)!!
        )

        startActivity(intent, options.toBundle())*/

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}