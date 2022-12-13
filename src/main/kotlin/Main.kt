import org.example.*
import java.lang.Math.abs
import java.lang.Math.log10

val vl:Frame = FrameDefaultImpl("|")
val hl:Frame = FrameDefaultImpl("-")


fun main() {
    val eL = ElementList()
    println(eL.elements)
    val empty: Frame = FrameDefaultImpl("    ", "    ", "    ")
    val frame = FrameDefaultImpl()
    var matrix =
        Array(eL.rows) { row -> Array<Frame>(eL.cols) { FrameDefaultImpl(*empty.lines.toTypedArray()) } }
    var atomicMatrix=Array(eL.rows) { row -> arrayOfNulls<Int>(eL.cols)}
    for (e in eL.elements) {
        matrix[e.period - 1][e.group - 1] = frame.elementBox(e)
        atomicMatrix[e.period-1][e.group-1] = e.atomicNumber
    }

    for (arr in matrix) {
        var previusFrame = arr.get(0)
        var loop = true
        var startIndex = 1
        do {
            for (i in startIndex until arr.size) {
                //println("(Height) Frame ${i-1}: ${arr.get(i-1).lines.size} | Frame ${i}: ${arr.get(i).lines.size}")
                previusFrame = previusFrame.atLeftOf(arr.get(i))
                //println("(Height) Frame ${i-1}: ${arr.get(i-1).lines.size} | Frame ${i}: ${arr.get(i).lines.size}")
            }
            val heightFirstFrame = arr.get(0).lines.size
            val heightLastFrame = arr.get(arr.size - 1).lines.size
            if (heightFirstFrame == heightLastFrame)
                loop = false
            else
                startIndex = 0

        } while (loop)
    }
    for (j in 0 until matrix.get(0).size) {
        var previusFrame = matrix.get(0).get(j)
        var loop = true
        var startIndex = 1
        do {
            for (i in startIndex until matrix.size) {
                previusFrame = previusFrame.onTopOf(matrix.get(i).get(j))
            }
            val widthFirstFrame = matrix.get(0).get(j).lines.get(0).length
            val widthLastFrame = matrix.get(matrix.size - 1).get(j).lines.get(0).length
            if (widthFirstFrame == widthLastFrame)
                loop = false
            else
                startIndex = 0
        } while (loop)
    }
    val table= Table(matrix, atomicMatrix)
    println(table.printTable())
}
