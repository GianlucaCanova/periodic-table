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
    for(e in eL.elements){
        println(frame.elementBox(e))
    }

}

