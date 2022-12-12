package org.example
interface Frame {

    fun atLeftOf(other: Frame): Frame

    fun onTopOf(other: Frame): Frame

    val lines: MutableList<String>

}