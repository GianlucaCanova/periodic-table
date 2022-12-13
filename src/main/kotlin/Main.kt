import org.example.Element
import org.example.ElementList
import org.example.Frame
import org.example.FrameDefaultImpl
import java.lang.Math.abs
import java.lang.Math.log10

val vl:Frame = FrameDefaultImpl("|")
val hl:Frame = FrameDefaultImpl("-")

fun main() {
    val eL = ElementList()
    println(eL.elements)
    val empty: Frame = FrameDefaultImpl("    ","    ","    ")
    val frame=FrameDefaultImpl()
    var matrix = Array(eL.rows) { row -> Array<Frame>(eL.cols){FrameDefaultImpl(*empty.lines.toTypedArray())} }
    for (e in eL.elements){
        matrix[e.period-1][e.group-1]= frame.elementBox(e)
    }

    for(arr in matrix){
        var previusFrame=arr.get(0)
        var loop=true
        var startIndex = 1
        do {
            for (i in startIndex until arr.size) {
                //println("(Height) Frame ${i-1}: ${arr.get(i-1).lines.size} | Frame ${i}: ${arr.get(i).lines.size}")
                previusFrame = previusFrame.atLeftOf(arr.get(i))
                //println("(Height) Frame ${i-1}: ${arr.get(i-1).lines.size} | Frame ${i}: ${arr.get(i).lines.size}")
            }
            val heightFirstFrame=arr.get(0).lines.size
            val heightLastFrame=arr.get(arr.size-1).lines.size
            if( heightFirstFrame == heightLastFrame )
                loop = false
            else
                startIndex = 0

        } while (loop)
    }
    for(j in 0 until matrix.get(0).size) {
        var previusFrame=matrix.get(0).get(j)
        var loop=true
        var startIndex = 1
        do {
            for (i in startIndex until matrix.size) {
                previusFrame = previusFrame.onTopOf(matrix.get(i).get(j))
            }
            val widthFirstFrame=matrix.get(0).get(j).lines.get(0).length
            val widthLastFrame=matrix.get(matrix.size-1).get(j).lines.get(0).length
            if ( widthFirstFrame == widthLastFrame )
                loop = false
            else
                startIndex = 0
        } while(loop)
    }
    println(printTable(matrix))
}

fun printTable(matrix: Array<Array<Frame>>): String{
    var result=""
    var nextRightFrame: Frame
    var nextBottomArray= matrix.get(0)
    var nextBottomFrame: Frame
    for(j in 0 until matrix.size){
        if(j<matrix.size-1){
            nextBottomArray=matrix.get(j+1)
        }
        val array=matrix.get(j)
        val first=array.get(0)
        var lines=ArrayList<String>()
        nextRightFrame=array.get(1)
        var rightHorizontalMargin=horizontalMarginChoice(first, nextRightFrame)
        if(j>0)
            first.lines.forEachIndexed { i, s -> if(i>0)  lines.add(s.substring(0,s.length-1)+rightHorizontalMargin.get(i)) }
        else
            first.lines.forEachIndexed { i, s -> lines.add(s.substring(0,s.length-1)+rightHorizontalMargin.get(i))}
        for(i in 1 until array.size){
            if(i!=array.lastIndex){
                nextRightFrame=array.get(i+1)
            }
            rightHorizontalMargin=horizontalMarginChoice(array.get(i), nextRightFrame)
            val diff=array.get(i).lines.size-lines.size
            for (j in 0 until lines.lastIndex){
                lines.set(j, lines.get(j)+array.get(i).lines.get(j+diff).substring(1,array.get(i).lines.get(j+diff).lastIndex)+rightHorizontalMargin.get(j+diff))
            }
            nextBottomFrame=nextBottomArray.get(i)
            val rightVerticalMargin=verticalMarginChoice(array.get(i), nextBottomFrame).substring(1)
            lines.set(lines.lastIndex, lines.get(lines.lastIndex)+rightVerticalMargin)
        }
        for(line in lines){
           result+=line+"\n"
        }
    }
    return result
}

fun horizontalMarginChoice(left: Frame, right: Frame): ArrayList<String>{
    var whichKeep : Frame? = null
    var result= ArrayList<String>()
    for(i in 0 until left.lines.size){
        if(whichKeep == null) {
            val leftChar = left.lines.get(i).get(0).toString()
            val rightChar = right.lines.get(i).get(0).toString()
            if (leftChar.equals(" ") and rightChar.equals(" ")){
                result.add(leftChar)
            }
            else{
                if(leftChar.equals(" ")){
                    result.add(rightChar)
                    whichKeep=right
                }
                else{
                    result.add(leftChar)
                    whichKeep=left
                }
            }
        }
        else{
            val toInsert = whichKeep.lines.get(i).get(0).toString()
            result.add(toInsert)
        }
    }
    return result
}

fun verticalMarginChoice(top: Frame, bottom: Frame): String{
    if(top.lines.get(0).contains(hl.lines.get(0))){
        return top.lines.get(0)
    }
    else{
        return bottom.lines.get(0)
    }
}