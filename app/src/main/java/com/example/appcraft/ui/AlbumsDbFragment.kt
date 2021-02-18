package com.example.appcraft.ui

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.appcraft.R
import com.example.appcraft.adapters.AlbumsAdapter
import com.example.appcraft.adapters.AlbumsAdapterDb
import com.example.appcraft.databinding.FragmentAlbumsDbBinding
import com.example.appcraft.db.AlbumsViewModel
import com.example.appcraft.db.albums.AlbumEntity
import com.example.appcraft.utils.Constants.ALBUM_ID_DATA
import com.example.appcraft.utils.Constants.ALBUM_TITLE_DATA
import kotlinx.android.synthetic.main.fragment_albums_db.*

class AlbumsDbFragment : Fragment(R.layout.fragment_albums_db), AlbumsAdapter.OnItemClickListener {

    private var _binding: FragmentAlbumsDbBinding? = null
    private val binding: FragmentAlbumsDbBinding get() = _binding!!

    private lateinit var albumsViewModel: AlbumsViewModel
    private lateinit var albumsAdapterDb: AlbumsAdapterDb

    private var isDatabaseEmpty = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAlbumsDbBinding.bind(view)

        albumsAdapterDb = AlbumsAdapterDb(this, requireContext())

        // Handling RecycleView and it's animation
        val lac: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_fall_down)

        albums_db_fragment_recycler_view.adapter = albumsAdapterDb
        albums_db_fragment_recycler_view.layoutAnimation = lac
        albums_db_fragment_recycler_view.startLayoutAnimation()


        albumsViewModel = ViewModelProvider(this).get(AlbumsViewModel::class.java)
        albumsViewModel.allAlbums.observe(requireActivity(), Observer { albums ->
            albums?.let {

                if (it.isEmpty()) {
                    isDatabaseEmpty = true
                    Toast.makeText(requireContext(), getString(R.string.albums_database_is_empty), Toast.LENGTH_SHORT).show()
                } else {
                    isDatabaseEmpty = false
                    albumsAdapterDb.setItems(it)
                    albumsAdapterDb.notifyDataSetChanged()
                }

            }
        })

        if (isDatabaseEmpty) {
            album_db_fragment_empty_text.visibility = View.VISIBLE
        } else {
            album_db_fragment_empty_text.visibility = View.GONE
        }

    }

    override fun onItemClick(v: View?, position: Int) {
        val clickedItem: AlbumEntity = albumsAdapterDb.getItemAt(position)

        val bundleData = Bundle()

        bundleData.putLong(ALBUM_ID_DATA, clickedItem.albumId)
        bundleData.putString(ALBUM_TITLE_DATA, clickedItem.albumTitle)

        findNavController().navigate(R.id.action_navigation_database_to_albumDetailsDbFragment, bundleData)
    }


}