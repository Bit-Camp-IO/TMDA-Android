package com.example.sharedui.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.example.shared.entities.Video

fun getTmdbImageLink(path: String?) =
    if (path != null) "https://image.tmdb.org/t/p/w500$path" else null

fun List<Video>.getParsedYoutubeList() =
    filter { it.site == "YouTube" && it.type == "Trailer" }
        .joinToString(separator = ",") { it.key }

fun Activity.openYouTubePlaylist(videoKeys: String) {
    val playlistUrl = "https://www.youtube.com/watch_videos?video_ids=$videoKeys"

    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(playlistUrl))
    intent.setPackage("com.google.android.youtube")

    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    } else {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(playlistUrl)))
    }
}