package org.example

import hl
import vl

class FrameDefaultImpl(vararg line: String): Frame {

    override val lines: List<String>



    init {
        lines= line.asList()
    }

    override fun atLeftOf(other: Frame): Frame {
        return this;
    }

    override fun onTopOf(other: Frame): Frame {
        return this;
    }

    override fun toString(): String {
        return lines.joinToString(separator= "\n")
    }


    fun numberOfDigits(num: Int): Int {
        return (Math.log10(Math.abs(num).toDouble()) + 1).toInt()
    }

    fun elementFrame(e: Element): Frame {
        return FrameDefaultImpl(e.symbol,e.atomicNumber.toString())
        /*val atomicLen = numberOfDigits(e.atomicNumber)
        var s = ArrayList<String>()
        if (e.symbol.length > atomicLen) {
            s.add(" " + e.symbol + " ")
            var temp = " "
            val diff = e.symbol.length - atomicLen
            for (i in 0 until diff) {
                temp += " "
            }
            temp += e.atomicNumber.toString() + " "
            s.add(temp)
        } else {
            var temp = " "
            val diff = atomicLen - e.symbol.length
            for (i in 0 until diff) {
                temp += " "
            }
            temp += e.symbol + " "
            s.add(temp)
            s.add(" " + e.atomicNumber.toString() + " ")
        }
        return FrameDefaultImpl(*s.toTypedArray())*/
    }

    fun elementBox(e: Element): Frame {
        val content=elementFrame(e)
        val lens = content.lines
        val maxLen = lens.stream().
        max(Comparator.comparingInt(String::length)).get().length;
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

    /*override fun toString(): String {
        var res=" "+lines[0]+"\n"
        val distance=lines[0].length
        for(i in lines[1]){
            res+=i
            for(j in lines[0]){
                res+=" "
            }
            res+=i+"\n"
        }
        res+=" "+lines[0]
        return res;
    }*/
}

