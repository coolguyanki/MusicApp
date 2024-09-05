package com.example.musicapp.models

data class SongModel(
    val id: String,
    val title: String,
    val subTitle: String,
    val url: String,
    val coverUrl:String
) {
    constructor() :this("","","","","")
}
