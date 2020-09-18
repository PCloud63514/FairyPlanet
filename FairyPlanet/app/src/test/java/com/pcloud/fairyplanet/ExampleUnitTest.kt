package com.pcloud.fairyplanet

import com.pcloud.fairyplanet.data.Frame
import com.pcloud.fairyplanet.data.Location
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun tesss() {
        var a = 1
        var i2 = intArrayOf()
        println(i2)
    }

    @Test //(5개 실패)
    fun codeTest1() {
        var new_id:String = 	"aaaaaaaaaa-.-.-.-@@@.............................."
        var regex = "[~!@#$%^&*()=+[{]}:?,<>]".toRegex()
        var answer = ""

        var new_id_temp = new_id.toLowerCase()

        var answerBuilder = StringBuilder(new_id_temp.replace(regex, ""))
        var index:Int = 0
        while(index < answerBuilder.length) {
            if (answerBuilder.get(index).equals('.') && '.'.equals(answerBuilder.getOrNull(index + 1)))
            {
                answerBuilder = answerBuilder.delete(index, index + 1)
            }
            else {
                index += 1
            }
        }
        answerBuilder = StringBuilder(answerBuilder.trim('.'))
        if(answerBuilder.isEmpty()) {
            answerBuilder = StringBuilder("a")
        }

        if(answerBuilder.length >= 16) {
            answerBuilder = StringBuilder(answerBuilder.delete(15, answerBuilder.length + 1).trim('.'))
        }

        while(answerBuilder.length < 3) {
            answerBuilder = answerBuilder.append(answerBuilder.lastOrNull())
        }
        answer = answerBuilder.toString()
        println(answer)
    }

    @Test
    fun codeTest2() {
        var orders: Array<String> = arrayOf("ABCDE", "AB", "CD", "ADE", "XYZ", "XYZ", "ACD")
        var coures: IntArray = intArrayOf(2, 3, 4)
        var answer:Array<String> = arrayOf<String>()
        var sortOrders = orders.sortedArrayWith(Comparator { s1:String, s2:String ->
            s1.length - s2.length
        })

        for(item in orders) {
            var equ = true
            var orderCount = 0
            for(item2 in orders.toList().minus(item)) {
                for(c in item.toCharArray()) {
                    if(!item2.toCharArray().contains(c)) {
                        equ = false
                        break
                    }
                }
                if(equ) {
                    orderCount += 1
                }
            }
            if(orderCount > 1) {
                answer.plus(item)
            }
        }


        for(item in answer) {
            println(item)
        }

        //for(item in orders) {
        //    for (i in orders.filter { item.toCharArray() contentEquals it.toCharArray() }) {
        //        println(i)
        //    }
        //}
    }

    @Test
    fun codeTest3() {

    }

    @Test
    fun imageSplit() {

    }

    @Test
    fun JSONParsingTest() {
        val jsonString = """
            {
            "person": [
                      {
                        "id": 0,
                        "name": "Mathews Parker",
                        "email": "mathewsparker@franscene.com"
                      },
                      {
                        "id": 1,
                        "name": "Dickson Clements",
                        "email": "dicksonclements@franscene.com"
                      },
                      {
                        "id": 2,
                        "name": "Pat Blair",
                        "email": "patblair@franscene.com"
                      },
                      {
                        "id": 3,
                        "name": "Estela Mckinney",
                        "email": "estelamckinney@franscene.com"
                      },
                      {
                        "id": 4,
                        "name": "Rivera Mcclain",
                        "email": "riveramcclain@franscene.com"
                      }
                    ]
            }
        """.trimIndent()
        val jsonObject = JSONObject(jsonString)
        val jsonArray = jsonObject.getJSONArray("person")
    }

    @Test
    fun getUISpriteContentMap() {
        var domain: String = "/Dongki/data.json"
        var jsonContent = ExampleUnitTest::class.java.getResource(domain)?.readText()!!.trimIndent()

        var jsonObject:JSONObject = JSONObject(jsonContent)
        var jsonArray: JSONArray = jsonObject.getJSONArray("frames")

        var frameMap:MutableMap<String,MutableList<Frame>> = mutableMapOf()

        (0..jsonArray.length().minus(1)).forEach { i ->
            val jObject: JSONObject = jsonArray.getJSONObject(i)
            val fileName = jObject.getString("filename")

            val frameJSONObject:JSONObject = jObject.getJSONObject("frame")
            val frame:Location = Location(
                x=frameJSONObject.getDouble("x").toFloat(),
                y=frameJSONObject.getDouble("y").toFloat(),
                w=frameJSONObject.getDouble("w").toFloat(),
                h=frameJSONObject.getDouble("h").toFloat()
            )

            val rotated:Boolean = jObject.getBoolean("rotated")
            val trimmed:Boolean = jObject.getBoolean("trimmed")

            val spriteSourceSizeJSONObject:JSONObject = jObject.getJSONObject("spriteSourceSize")
            val spriteSourceSize:Location = Location(
                x=spriteSourceSizeJSONObject.getDouble("x").toFloat(),
                y=spriteSourceSizeJSONObject.getDouble("y").toFloat(),
                w=spriteSourceSizeJSONObject.getDouble("w").toFloat(),
                h=spriteSourceSizeJSONObject.getDouble("h").toFloat()
            )
            val sourceSizeJSONObject:JSONObject = jObject.getJSONObject("sourceSize")
            val sourceSize:Location = Location(0f,0f,sourceSizeJSONObject.getDouble("w").toFloat(),sourceSizeJSONObject.getDouble("h").toFloat())

            val Frame: Frame = Frame(fileName,  frame, rotated, trimmed, spriteSourceSize, sourceSize)

            val _filename:String = fileName.split("-")[0]

            if(frameMap.get(_filename).isNullOrEmpty()) {
                var list: MutableList<Frame> = mutableListOf(Frame)
                frameMap.set(_filename, list)
            }
            else {
                frameMap.get(_filename)?.add(Frame)
            }

            println("Frame:${Frame.toString()}")
        }
    }

    @Test
    fun extractJSON() {
        var domain: String = "/Dongki/sprite.json"
        var jsonContent = ExampleUnitTest::class.java.getResource(domain)?.readText()
        println(jsonContent)
    }
}