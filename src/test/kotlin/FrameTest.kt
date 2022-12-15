import org.example.ElementList
import org.example.Frame
import org.example.FrameDefaultImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FrameTest {
    val f1=FrameDefaultImpl("    ", "    ", "    ")
    val f2=FrameDefaultImpl(" ----- ", "|  1  |", "|  H  |","|     |"," ----- ")
    val els=ElementList()
    @Test
    fun atLeftOf_Test(){
        f1.atLeftOf(f2)
        Assertions.assertEquals(f1.lines.size, f2.lines.size)
    }

    @Test
    fun onTopOf_Test(){
        f1.onTopOf(f2)
        Assertions.assertEquals(f1.lines.get(0).length, f2.lines.get(0).length)
    }

    @Test
    fun elementFrame(){
        val case=2
        val f=FrameDefaultImpl()
        val el=els.elements.get(case)
        val res=f.elementFrame(el)
        Assertions.assertEquals(res.lines, listOf(el.atomicNumber.toString(),el.symbol))
    }

    @Test
    fun elementBox(){
        val case=15
        val f=FrameDefaultImpl()
        val el=els.elements.get(case)
        val res=f.elementBox(el)
        Assertions.assertTrue(res.lines.get(0).contains(Regex(pattern = "^ -+ $", options = setOf(RegexOption.IGNORE_CASE))))
        Assertions.assertTrue(res.lines.get(1).contains(Regex(pattern = "^\\|[ ]+[0-9]+[ ]+\\|$", options = setOf(RegexOption.IGNORE_CASE))))
        Assertions.assertTrue(res.lines.get(2).contains(Regex(pattern = "^\\|[ ]+[a-zA-Z]+[ ]+\\|\$", options = setOf(RegexOption.IGNORE_CASE))))
        Assertions.assertTrue(res.lines.get(res.lines.size-1).contains(Regex(pattern = "^ -+ $", options = setOf(RegexOption.IGNORE_CASE))))
    }

}