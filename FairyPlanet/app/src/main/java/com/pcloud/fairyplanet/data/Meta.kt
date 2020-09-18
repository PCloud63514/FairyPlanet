package com.pcloud.fairyplanet.data

data class Meta(
    val app:String, val version:String,
    val image:String, val format:String,
    var sourceSize:Location, val size:Location,
    val scale:Int, val smartUpdate:String
) {
}