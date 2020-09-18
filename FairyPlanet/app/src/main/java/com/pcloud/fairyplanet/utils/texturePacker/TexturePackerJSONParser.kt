package com.pcloud.fairyplanet.utils.texturePacker

import android.app.Application
import android.content.Context
import com.pcloud.fairyplanet.data.Frame
import com.pcloud.fairyplanet.data.Location
import com.pcloud.fairyplanet.data.Meta
import com.pcloud.fairyplanet.data.SpriteSheetConfigInformation
import org.json.JSONObject
import java.io.FileInputStream
import java.io.InputStream
import java.nio.file.Paths

/**
 * 다른 언어에 비해 Kotlin은 생성자 종류가 다양하다.
 * 주 생성자(Primary Constructor)
 * ex) class Templet(_id: String, _index: Int)
 * 주 생성자는 코드블럭이 존재하지 않아 코드 추가가 불가능하다. 만약 생성자 단계에서 파라미터를 검증하거나 수정해야한다면 init block을 사용해야한다.
 * 부 생성자
 *
 * */
/**
 *
 * */
fun extractSpriteSheetConfigInformation(context: Context, domain: String): SpriteSheetConfigInformation? {
    var jsonString = ""
    context.resources.assets.open(domain + "/data.json").let {
        jsonString = it.bufferedReader().use{it.readText()}
        it.close()
    }
    if (!jsonString.isEmpty()) {
        var jsonObject = JSONObject(jsonString)
        var metaJSONObject = jsonObject.getJSONObject("meta")
        var sourceSizeLocationJSONObject = metaJSONObject.getJSONObject("sourceSize")
        var sizeLocationJSONObject = metaJSONObject.getJSONObject("size")

        var meta = Meta(
            app=metaJSONObject.getString("app"),
            version=metaJSONObject.getString("version"),
            image=metaJSONObject.getString("image"),
            format=metaJSONObject.getString("format"),
            sourceSize = Location(0f, 0f, sourceSizeLocationJSONObject.getDouble("w").toFloat(), sourceSizeLocationJSONObject.getDouble("h").toFloat()),
            size=Location(0f, 0f, sizeLocationJSONObject.getDouble("w").toFloat(), sizeLocationJSONObject.getDouble("h").toFloat()),
            scale=metaJSONObject.getInt("scale"),
            smartUpdate = metaJSONObject.getString("smartupdate")
        )
        var frams = getJSONFrames(jsonObject)

        return SpriteSheetConfigInformation(meta, frams)
    }
    return null
}

private fun getJSONFrames(jsonObject:JSONObject):MutableMap<String, MutableList<Frame>> {
    var jsonArray = jsonObject.getJSONArray("frames")
    var frameMap = mutableMapOf<String, MutableList<Frame>>()

    for(i in 0..jsonArray.length().minus(1)) {
        val jobj = jsonArray.getJSONObject(i)
        val fileName = jobj.getString("filename")

        val frameJSONObject = jobj.getJSONObject("frame")
        val frame = Location(frameJSONObject.getDouble("x").toFloat(),
            frameJSONObject.getDouble("y").toFloat(),
            frameJSONObject.getDouble("w").toFloat(),
            frameJSONObject.getDouble("h").toFloat())
        val rotated = jobj.getBoolean("rotated")
        val trimmed = jobj.getBoolean("trimmed")
        val spriteSourceSizeJSONObject = jobj.getJSONObject("spriteSourceSize")
        val spriteSourceSize = Location(spriteSourceSizeJSONObject.getDouble("x").toFloat(),
            spriteSourceSizeJSONObject.getDouble("y").toFloat(),
            spriteSourceSizeJSONObject.getDouble("w").toFloat(),
            spriteSourceSizeJSONObject.getDouble("h").toFloat())
        val sourceSizeJSONObject = jobj.getJSONObject("sourceSize")
        val sourceSize = Location(0f, 0f, sourceSizeJSONObject.getDouble("w").toFloat(),
            sourceSizeJSONObject.getDouble("h").toFloat())
        val FrameObject = Frame(fileName, frame, rotated, trimmed, spriteSourceSize, sourceSize)

        val key = fileName.split("/")[0]

        if(frameMap.get(key).isNullOrEmpty()) {
            var list = mutableListOf<Frame>(FrameObject)
            frameMap.set(key, list)
        }
        else {
            frameMap.get(key)!!.add(FrameObject)
        }
    }

    return  frameMap
}