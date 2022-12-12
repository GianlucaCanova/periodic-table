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
    val frame=FrameDefaultImpl()
    var matrix = Array(eL.rows) { row -> Array<Frame>(eL.cols){FrameDefaultImpl()} }
    for (e in eL.elements){
        matrix[e.period-1][e.group-1]= frame.elementBox(e)
    }
    for(arr in matrix){
        for(f in arr){
            println(f)
        }
    }
}

