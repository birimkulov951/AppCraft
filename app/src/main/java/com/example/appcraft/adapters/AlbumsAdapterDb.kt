package com.example.appcraft.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.appcraft.R
import com.example.appcraft.db.albums.AlbumEntity
import com.example.appcraft.ui.AlbumsDbFragment
import kotlinx.android.synthetic.main.albums_list_item.view.*

class AlbumsAdapterDb(
    private val listener: AlbumsDbFragment, val context: Context
) :
    RecyclerView.Adapter<AlbumsAdapterDb.AlbumsViewHolder>(){

    private var albumItem = emptyList<AlbumEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.albums_list_item, parent, false)

        return AlbumsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {
        val currentItem = albumItem[position]

        holder.itemTitle.text = currentItem.albumTitle

    }

    override fun getItemCount() = albumItem.size

    internal fun setItems(albumItem: List<AlbumEntity>) {
        this.albumItem = albumItem
    }

    fun getItemAt(position: Int): AlbumEntity {
        return albumItem[position]
    }

    //**********************************************************************************************

    inner class AlbumsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val itemTitle: TextView = itemView.album_title

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            listener.onItemClick(v,this.adapterPosition)

        }

    }

    interface OnItemClickListener {
        fun onItemClick(v: View?,position: Int)
    }

}