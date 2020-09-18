package com.pcloud.fairyplanet.data

data class SpriteSheetConfigInformation(
    val meta:Meta, val frames:MutableMap<String, MutableList<Frame>>
)