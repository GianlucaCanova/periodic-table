package org.example

import hl
import vl

class FrameDefaultImpl(vararg line: String): Frame {

    override val lines: MutableList<String>


    init {
        lines= ArrayList(line.asList())
    }

    override fun atLeftOf(other: Frame): Frame {
        if(lines.size!=other.lines.size){
            if(lines.size<other.lines.size) {
                val s=vl.lines.get(0)+" ".repeat(lines.get(0).length-2)+vl.lines.get(0)
                for(i in 0 until other.lines.size-lines.size){
                    lines.add(lines.size-1,s)
                }
            }
            else{
                val s=vl.lines.get(0)+" ".repeat(other.lines.get(0).length-2)+vl.lines.get(0)
                for(i in 0 until lines.size-other.lines.size){
                    other.lines.add(other.lines.size-1,s)
                }
            }
        }
        return other
    }

    override fun onTopOf(other: Frame): Frame {
        val thisLength = lines.get(0).length
        val otherLength = other.lines.get(0).length
        if(thisLength != otherLength){
            if(thisLength < otherLength) {
                val diff = otherLength - thisLength
                val leftDiff=diff/2+(diff%2)
                for(i in 0 until lines.size){
                    val _line=lines.get(i)
                    when(i){
                        0, lines.size-1 -> lines.set(i, _line+hl.lines.get(0).repeat(diff))
                        else ->{
                            lines.set(i, _line.substring(0,2)+" ".repeat(leftDiff)+_line.substring(2,_line.length-2)+" ".repeat(diff-leftDiff)+_line.substring(_line.length-2))
                        }
                    }
                }
            }
            else{
                val diff = thisLength - otherLength
                val leftDiff=diff/2+(diff%2)
                for(i in 0 until other.lines.size){
                    val _line=other.lines.get(i)
                    when(i){
                        0, other.lines.size-1 -> other.lines.set(i, _line+hl.lines.get(0).repeat(diff))
                        else ->{
                            other.lines.set(i, _line.substring(0,2)+" ".repeat(leftDiff)+_line.substring(2,_line.length-2)+" ".repeat(diff-leftDiff)+_line.substring(_line.length-2))
                        }
                    }
                }
            }
        }
        return other
    }

    override fun toString(): String {
        return lines.joinToString(separator= "\n")
    }


    fun numberOfDigits(num: Int): Int {
        return (Math.log10(Math.abs(num).toDouble()) + 1).toInt()
    }

    fun elementFrame(e: Element): Frame {
        return FrameDefaultImpl(e.symbol,e.atomicNumber.toString())

    }

    fun elementBox(e: Element): Frame {
        val content=elementFrame(e)
        val lens = content.lines
        val maxLen=lens.maxWith(Comparator.comparingInt{it.length}).length
        /*val maxLen = lens.stream().
        max(Comparator.comparingInt(String::length)).get().length;*/
        var s = ArrayList<String>()
        var temp: String
        val spaceInside=maxLen+2
        val totSpace=spaceInside+2
        val totRows= content.lines.size+2
        for(j in 0 until totRows) {
            temp=""
            when(j){
                0, (totRows-1) -> {
                    temp += hl.lines.get(0).repeat(totSpace)
                }
                else ->{
                    temp+=vl.lines.get(0)
                    val numSpaces=spaceInside-content.lines.get(j-1).length
                    val leftSpaces=numSpaces/2
                    val rightSpaces=numSpaces-leftSpaces
                    temp+=" ".repeat(leftSpaces)
                    temp+=content.lines.get(j-1)
                    temp+=" ".repeat(rightSpaces)
                    temp+=vl.lines.get(0)
                }
            }
            s.add(temp)
        }
        return FrameDefaultImpl(*s.toTypedArray())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FrameDefaultImpl

        if (lines != other.lines) return false

        return true
    }

    override fun hashCode(): Int {
        return lines.hashCode()
    }


}

