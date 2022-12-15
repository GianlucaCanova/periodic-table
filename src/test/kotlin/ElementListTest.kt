import org.example.ElementList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ElementListTest {

    @Test
    fun stringToElementList_Test(){
        val els=ElementList()
        Assertions.assertEquals(els.elements.size, 118)
    }
}