package com.balistos.balistos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class PlaylistAdapter(
    private val context: Context,
    private val dataSource: Array<Playlist>
) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    //2
    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    //3
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //4
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get view for row item
        val playlist = getItem(position) as Playlist
        val rowView = inflater.inflate(R.layout.playlist_item, parent, false)
        val titleTextView = rowView.findViewById(R.id.playlist_title) as TextView
        val indexTextView = rowView.findViewById(R.id.playlist_index) as TextView
        val authorTextView = rowView.findViewById(R.id.playlist_author) as TextView
        titleTextView.text = playlist.title
        indexTextView.text = (position + 1).toString()
        authorTextView.text = playlist.author
        return rowView
    }


}

