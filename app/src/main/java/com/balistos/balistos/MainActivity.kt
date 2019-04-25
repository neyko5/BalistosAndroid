package com.balistos.balistos

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById<ListView>(R.id.playlist_list)
        listView.setDivider(null);

        val playlistList = arrayOf(Playlist("Kolega", "1", "Jernej"), Playlist("Majstorica","2", "mojster" ), Playlist("Top playlist", "3", "Milorad"))

        val adapter = PlaylistAdapter(this, playlistList)
        listView.adapter = adapter

        val context = this
    }
}
