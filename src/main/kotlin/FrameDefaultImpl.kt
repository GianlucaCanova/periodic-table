package org.example

import hl
import vl
import java.util.regex.Pattern

class FrameDefaultImpl(vararg line: String): Frame {

    override val lines: MutableList<String>
    val SPACE : String= " "

    init {
        lines= ArrayList(line.asList())
    }


    override fun atLeftOf(other: Frame): Frame {
        if(lines.size!=other.lines.size){
            if(lines.size<other.lines.size) {
                val s=lines.get(0).get(0).toString()+SPACE.repeat(lines.get(0).length-2)+lines.get(0).get(0)
                for(i in 0 until other.lines.size-lines.size){
                    lines.add(lines.size-1,s)
                }
            }
            else{
                val s=other.lines.get(0).get(0).toString()+SPACE.repeat(other.lines.get(0).length-2)+other.lines.get(0).get(0)
                for(i in 0 until lines.size-other.lines.size){
                    other.lines.add(other.lines.size-1,s)
                }
            }
        }
        return other
    }

    override fun onTopOf(other: Frame): Frame {
        //alignment(other)
        val thisLength = lines.get(0).length
        val otherLength = other.lines.get(0).length
        val patternLetters= Pattern.compile("\\p{Alpha}")
        if(thisLength != otherLength){
            if(thisLength < otherLength) {
                val diff = otherLength - thisLength

                var leftDiff=0
                for(i in 0 until lines.size){
                    val matcher1=patternLetters.matcher(lines.get(i))
                    val matcher2=patternLetters.matcher(other.lines.get(i))
                    if(matcher1.find() && matcher2.find()){
                        val start1=matcher1.start()
                        val start2=matcher2.start()
                        if(start1!=start2){
                            if(start1<start2){
                                leftDiff=start2-start1
                            }
                        }
                        break
                    }
                }
                for(i in 0 until lines.size){
                    val _line=lines.get(i)
                    when(i){
                        0, lines.size-1 -> lines.set(i, _line.substring(0,_line.length-1)+(lines.get(0).get(1).toString()).repeat(diff)+_line.get(_line.length-1).toString())
                        else ->{
                            lines.set(i, _line.substring(0,2)+SPACE.repeat(leftDiff)+_line.substring(2,_line.length-2)+SPACE.repeat(diff-leftDiff)+_line.substring(_line.length-2))
                        }
                    }
                }
            }
            else{
                val diff = thisLength - otherLength
                var leftDiff=0
                for(i in 0 until lines.size){
                    val matcher1=patternLetters.matcher(lines.get(i))
                    val matcher2=patternLetters.matcher(other.lines.get(i))
                    if(matcher1.find() && matcher2.find()){
                        val start1=matcher1.start()
                        val start2=matcher2.start()
                        if(start1!=start2){
                            if(start1<start2){
                                leftDiff=start2-start1
                            }
                        }
                        break
                    }
                }
                for(i in 0 until other.lines.size){
                    val _line=other.lines.get(i)
                    when(i){
                        0, other.lines.size-1 -> other.lines.set(i, _line.substring(0,_line.length-1)+(other.lines.get(0).get(1).toString()).repeat(diff)+_line.get(_line.length-1).toString())
                        else ->{
                            other.lines.set(i, _line.substring(0,2)+SPACE.repeat(leftDiff)+_line.substring(2,_line.length-2)+SPACE.repeat(diff-leftDiff)+_line.substring(_line.length-2))
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

        return FrameDefaultImpl(e.atomicNumber.toString(),e.symbol)

    }

    fun elementBox(e: Element): Frame {
        val content=elementFrame(e)
        val lens = content.lines
        val maxLen=lens.maxWith(Comparator.comparingInt{it.length}).length

        var s = ArrayList<String>()
        var temp: String
        val spaceInside=maxLen+2
        val totSpace=spaceInside+2
        val totRows= content.lines.size+3
        for(j in 0 until totRows) {
            temp=""
            when(j){
                0, (totRows-1) -> {
                    temp += SPACE+ hl.lines.get(0).repeat(spaceInside) + SPACE
                }
                totRows-2 ->{
                    temp += vl.lines.get(0)+ SPACE.repeat(spaceInside)+ vl.lines.get(0)
                }
                else ->{
                    temp+=vl.lines.get(0)
                    val numSpaces=spaceInside-content.lines.get(j-1).length
                    val leftSpaces=numSpaces/2
                    val rightSpaces=numSpaces-leftSpaces
                    temp+=SPACE.repeat(leftSpaces)
                    temp+=content.lines.get(j-1)
                    temp+=SPACE.repeat(rightSpaces)
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

