import org.example.ElementList
import org.example.Frame
import org.example.FrameDefaultImpl
import org.example.Table
import org.junit.*;
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.regex.Matcher

class TableTest {

    val caseTopMarginBox=Regex(pattern = "^[-\\s]+$", options = setOf(RegexOption.IGNORE_CASE))
    val caseLetters=Regex(pattern = "^[\\s\\\\\\/\\|a-zA-Z]+$", options = setOf(RegexOption.IGNORE_CASE))
    val caseNumber=Regex(pattern = "^[\\s\\\\\\/\\|0-9]+$", options = setOf(RegexOption.IGNORE_CASE))
    val caseLineBox=Regex(pattern = "^[\\s\\\\\\/\\|]+$", options = setOf(RegexOption.IGNORE_CASE))
    val caseSpaces=Regex(pattern = "^\\s+$", options = setOf(RegexOption.IGNORE_CASE))

    @Test
    fun printTable_Test(){
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
        checkOnRight(matrix)
        checkOnBottom(matrix)
        val table= Table(matrix, atomicMatrix)
        val result=table.printTable()
        val cases= listOf<Regex>(caseTopMarginBox,
            caseLineBox,
            caseSpaces)
        val ripetibleCases= listOf<Regex>(caseLineBox,caseSpaces)
        var lastCase: Regex? =null

        var allLines=result.split("\r?\n|\r".toRegex()).toMutableList()
        allLines.remove("")
        for(line in allLines){
            var error=false
            var case: Regex? =null
            if(line.matches(caseTopMarginBox)){
                case=caseTopMarginBox
            }
            else{
                if(line.matches(caseSpaces)){
                    case=caseSpaces
                }
                else{
                    if (line.matches(caseLineBox)){
                        case=caseLineBox

                    }
                    else{
                        if(line.matches(caseLetters)){
                            case=caseLetters
                        }
                        else{
                            if(line.matches(caseNumber)){
                                case=caseNumber
                            }
                            else{
                                error=true
                            }
                        }

                    }
                }
            }
            if(lastCase!=null && case!=null){
                if(lastCase===case && ripetibleCases.contains(case))
                    Assertions.assertTrue(false)
            }
            lastCase=case
            Assertions.assertFalse(error, line)




        }

    }

}