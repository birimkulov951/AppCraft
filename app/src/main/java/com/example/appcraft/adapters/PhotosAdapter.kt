package com.example.appcraft.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.appcraft.R
import com.example.appcraft.model.photos.PhotosListItem
import kotlinx.android.synthetic.main.photos_list_item.view.*

class PhotosAdapter(
    private val listener: OnItemClickListener, val context: Context
) :
    RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>(){

    private var photosItem = emptyList<PhotosListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.photos_list_item, parent, false)

        return PhotosViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val currentItem = photosItem[position]

        try{
            holder.itemTitle.text = currentItem.title
        } catch (e: Exception) {
            holder.itemTitle.text = context.getString(R.string.not_found)
        }

        try{
            val url = GlideUrl(
                currentItem.thumbnailUrl, LazyHeaders.Builder()
                    .addHeader("User-Agent", "your-user-agent")
                    .build()
            )

            Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.placeholder_photo)
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .into(holder.itemUrl)
        } catch (e: Exception) {

            holder.itemUrl.setImageResource(R.drawable.placeholder_photo)

        }

        //  Set unique transition name for every item in your RecyclerView's adapter in onBindViewHolder method.
        ViewCompat.setTransitionName(holder.itemUrl, "TransitionId-"+currentItem.id.toString())

        // Added button listeners
        holder.itemUrl.setOnClickListener{
            val photoItem: PhotosListItem = getItemAt(position)

            listener.onPhotoClick(photoItem,position,holder.itemUrl)
        }

    }

    override fun getItemCount() = photosItem.size

    internal fun setItems(photosItem: List<PhotosListItem>) {
        this.photosItem = photosItem
    }

    fun getItemAt(position: Int): PhotosListItem {
        return photosItem[position]
    }

    //**********************************************************************************************

    inner class PhotosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val itemTitle: TextView = itemView.photo_title
        val itemUrl: ImageView = itemView.rounded_photo

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            listener.onItemClick(v,this.adapterPosition)

        }

    }

    interface OnItemClickListener {
        fun onItemClick(v: View?,position: Int)
        fun onPhotoClick(photoItem: PhotosListItem,position: Int,imageView: ImageView)
    }

}