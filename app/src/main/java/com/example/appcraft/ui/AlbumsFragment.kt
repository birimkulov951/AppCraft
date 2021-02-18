package com.example.appcraft.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appcraft.R
import com.example.appcraft.adapters.AlbumsAdapter
import com.example.appcraft.databinding.FragmentAlbumsBinding
import com.example.appcraft.model.albums.AlbumsListItem
import com.example.appcraft.retrofit.ApiService
import com.example.appcraft.retrofit.RetrofitInstance
import com.example.appcraft.utils.Constants.ALBUM_ID_DATA
import com.example.appcraft.utils.Constants.ALBUM_TITLE_DATA
import com.example.appcraft.utils.Constants.ALBUM_USER_ID
import kotlinx.android.synthetic.main.fragment_albums.*
import kotlinx.android.synthetic.main.fragment_albums.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumsFragment : Fragment(R.layout.fragment_albums), AlbumsAdapter.OnItemClickListener {

    private val TAG = "AlbumsFragment"

    private var _binding: FragmentAlbumsBinding? = null
    private val binding: FragmentAlbumsBinding get() = _binding!!

    private lateinit var albumsAdapter: AlbumsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAlbumsBinding.bind(view)

        getAlbumsList()
    }


    private fun getAlbumsList() {

        val retroInstance = RetrofitInstance.getRetrofitInstance().create(ApiService::class.java)
        val call = retroInstance.getAlbumsList()

        call.enqueue(object : Callback<MutableList<AlbumsListItem>> {
            override fun onResponse(call: Call<MutableList<AlbumsListItem>>, response: Response<MutableList<AlbumsListItem>>) {

                albums_fragment_progress_bar.visibility = View.GONE

                if (response.isSuccessful) {

                    albumsAdapter = AlbumsAdapter(this@AlbumsFragment, requireContext())
                    albumsAdapter.setItems(response.body() as MutableList<AlbumsListItem>)

                    // Handling RecycleView and it's animation
                    val lac: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_fall_down)

                    albums_fragment_recycler_view.adapter = albumsAdapter
                    albums_fragment_recycler_view.layoutAnimation = lac
                    albums_fragment_recycler_view.startLayoutAnimation()

                } else {
                    Toast.makeText(requireContext(), "Unknown error", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<MutableList<AlbumsListItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: $t")
                Toast.makeText(requireContext(), "Unknown error", Toast.LENGTH_SHORT).show()
                albums_fragment_progress_bar.visibility = View.GONE
            }

        })
    }

    override fun onItemClick(v: View?, position: Int) {
        val clickedItem: AlbumsListItem = albumsAdapter.getItemAt(position)

        val bundleData = Bundle()

        bundleData.putLong(ALBUM_ID_DATA, clickedItem.id)
        bundleData.putString(ALBUM_TITLE_DATA,clickedItem.title)
        bundleData.putLong(ALBUM_USER_ID, clickedItem.userId)


        findNavController().navigate(R.id.action_navigation_albums_to_albumDetailsFragment, bundleData)
    }

}