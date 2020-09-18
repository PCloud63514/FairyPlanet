package com.pcloud.fairyplanet.data

/**
 * Kotlin의 data class는 Java의 POJO(Plain Old Java Object) 클래스 같은 보일러 플레이트 코드 형식을 벗어난 방식이다.
 * data class 키워드를 이용해 생성자에 parameter만 정의하면 생성자, getter&setter, canonical method까지 알아서 생성한다.
 *
 * Canonical Method(캐노니컬 메소드) 는 Any에 선언된 메소드다. (Java의 Object == Kotlin의 Any) 즉 equlas, hashCode, toString이 자동 구현되어 있다는 뜻.
 *
 * 최소 조건
 * - 기본 생성자에 최소 하나의 파라미터가 있어야한다.
 * - 파라미터는 val이나 var여만 한다.
 * -- Kotlin은 Java와 달리 변수선언시 val과 var를 사용하며 타입선언 없이 바로 값을 할당할 수 있다.
 * val은 불변(immutable type)형이며 var은 가변(mutable type)이다.
 *
 * - data class는 abstract, open, sealed, inner 형식이 될 수 없다.
 * - inner 키워드가 적용된 클래스는 외부 클래스의 멤버를 참조할 수 있다.
 * */
data class Frame (
    val fileName: String,
    val frame: Location,
    val rotated: Boolean,
    val trimmed: Boolean,
    val spriteSourceSize: Location,
    val sourceSize: Location
)
{
    override fun toString(): String {
        var sb:StringBuilder = StringBuilder()
        sb.append("FileName:$fileName\n")
        sb.append("frame:{ x:${frame.x} y:${frame.y } w:${frame.w} h:${frame.h} }\n")
        sb.append("rotated:${rotated} \n")
        sb.append("trimmed:${trimmed} \n")
        sb.append("spriteSourceSize: { x:${spriteSourceSize.x} y:${spriteSourceSize.y} w:${spriteSourceSize.w} h:${spriteSourceSize.h} }\n")
        sb.append("sourceSize: { w:${sourceSize.w} h:${sourceSize.h} } \n")

        return sb.toString()
    }

}