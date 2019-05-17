package com.balistos.balistos.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.balistos.balistos.databinding.PlaylistItemBinding
import com.balistos.balistos.network.Playlist


/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 * @param onClick a lambda that takes the
 */
class PlaylistsAdapter( val onClickListener: OnClickListener ) :
    ListAdapter<Playlist, PlaylistsAdapter.PlaylistViewHolder>(DiffCallback) {
    /**
     * The MarsPropertyViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [MarsProperty] information.
     */
    class PlaylistViewHolder(private var binding: PlaylistItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(playlist: Playlist, position: Int) {
            binding.playlist = playlist
            binding.position = (position + 1).toString()
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val playlist = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(playlist)
        }
        holder.bind(playlist, position)
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [MarsProperty]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Playlist>() {
        override fun areItemsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
            return oldItem.id == newItem.id
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder(PlaylistItemBinding.inflate(LayoutInflater.from(parent.context)))
    }



    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [MarsProperty]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [MarsProperty]
     */
    class OnClickListener(val clickListener: (playlist: Playlist) -> Unit) {
        fun onClick(playlist: Playlist) = clickListener(playlist)
    }
}
