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
    val empty: Frame = FrameDefaultImpl("---","| |","---")
    val frame=FrameDefaultImpl()
    var matrix = Array(eL.rows) { row -> Array<Frame>(eL.cols){FrameDefaultImpl(*empty.lines.toTypedArray())} }
    for (e in eL.elements){
        matrix[e.period-1][e.group-1]= frame.elementBox(e)
    }
    for(arr in matrix){
        for(f in arr){
            println(f)
        }
    }

    println(printTable(matrix))
}

fun printTable(matrix: Array<Array<Frame>>): String{
    var result=""
    for(array in matrix){
        val first=array.get(0)
        var lines=ArrayList<String>()
        first.lines.forEach { s -> lines.add(s+"")}
        for(i in 1 until array.size){
            for (j in 0 until lines.size){
                lines.set(j, lines.get(j)+array.get(i).lines.get(j))
            }
        }
        for(line in lines){
           result+=line+"\n"
        }
    }
    return result
}