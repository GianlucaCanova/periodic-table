package org.example

import hl

class Table(val matrix: Array<Array<Frame>>, val elementMatrix: Array<Array<Int?>>) {

    val specialCases: List<Frame>
    var map: MutableMap<Frame, List<String>>
    init{
       map = mutableMapOf()
       val specialCasesCordinates = searchingSpecialCase()
       specialCases=ArrayList<Frame>()
       for(case in specialCasesCordinates){
           val frame=matrix.get(case.indexRow).get(case.indexCol)
           map.put(frame, case.marginRight)
           specialCases.add(frame)
       }
    }
    fun printTable(): String {
        var result = ""
        var nextRightFrame: Frame
        var nextBottomArray = matrix.get(0)
        var nextBottomFrame: Frame
        for (j in 0 until matrix.size) {
            if (j < matrix.size - 1) {
                nextBottomArray = matrix.get(j + 1)
            }
            val array = matrix.get(j)
            val first = array.get(0)
            var lines = ArrayList<String>()
            nextRightFrame = array.get(1)
            var rightHorizontalMargin = horizontalMarginChoice(first, nextRightFrame)
            if (j > 0 && j != 7)
                first.lines.forEachIndexed { i, s ->
                    if (i > 0) lines.add(
                        s.substring(
                            0,
                            s.length - 1
                        ) + rightHorizontalMargin.get(i)
                    )
                }
            else
                first.lines.forEachIndexed { i, s ->
                    lines.add(
                        s.substring(0, s.length - 1) + rightHorizontalMargin.get(
                            i
                        )
                    )
                }
            for (i in 1 until array.size) {
                if (i != array.lastIndex) {
                    nextRightFrame = array.get(i + 1)
                }
                rightHorizontalMargin = horizontalMarginChoice(array.get(i), nextRightFrame)
                val diff = array.get(i).lines.size - lines.size
                for (j in 0 until lines.lastIndex) {
                    lines.set(
                        j,
                        lines.get(j) + array.get(i).lines.get(j + diff).substring(
                            1,
                            array.get(i).lines.get(j + diff).lastIndex
                        ) + rightHorizontalMargin.get(j + diff)
                    )
                }
                nextBottomFrame = nextBottomArray.get(i)
                val rightVerticalMargin = verticalMarginChoice(array.get(i), nextBottomFrame).substring(1)
                lines.set(lines.lastIndex, lines.get(lines.lastIndex) + rightVerticalMargin)
            }
            for (line in lines) {
                result += line + "\n"
            }
            if (j == 6) {
                val blankLine = " ".repeat(lines.get(0).length)
                for (i in 0 until 3)
                    result += blankLine + "\n"
            }
        }
        return result
    }

    fun horizontalMarginChoice(left: Frame, right: Frame): List<String> {
        var result = ArrayList<String>()
        for(case in specialCases){
            if(case===left)
                return map.getOrDefault(case, ArrayList<String>())
        }
        var whichKeep: Frame? = null
        for (i in 0 until left.lines.size) {
            if (whichKeep == null) {
                val leftChar = left.lines.get(i).get(0).toString()
                val rightChar = right.lines.get(i).get(0).toString()
                if (leftChar.equals(" ") and rightChar.equals(" ")) {
                    result.add(leftChar)
                } else {
                    if (leftChar.equals(" ")) {
                        result.add(rightChar)
                        whichKeep = right
                    } else {
                        result.add(leftChar)
                        whichKeep = left
                    }
                }
            } else {
                val toInsert = whichKeep.lines.get(i).get(0).toString()
                result.add(toInsert)
            }
        }
        return result
    }

    fun verticalMarginChoice(top: Frame, bottom: Frame): String {
        if (top.lines.get(0).contains(hl.lines.get(0))) {
            return top.lines.get(0)
        } else {
            return bottom.lines.get(0)
        }
    }

    fun specialHorizontalMargin(height: Int, lastSymbol: String): List<String> {
        var lastSpecialChar = lastSymbol
        var result = ArrayList<String>()
        result.add(" ")
        for (i in 1 until height - 1) {
            if (lastSpecialChar.equals("\\")) {
                lastSpecialChar = "/"
            } else {
                lastSpecialChar = "\\"
            }
            result.add(lastSpecialChar)
        }
        result.add(" ")
        return result
    }

    fun searchingSpecialCase(): List<SpecialCordinatesAndMargin> {
        var symbol="\\"
        var result = ArrayList<SpecialCordinatesAndMargin>()
        for(j in 0 until elementMatrix.size){
            var previusElement=elementMatrix.get(j).get(0)
            for(i in 1 until elementMatrix.get(j).size){
                val currentElement=elementMatrix.get(j).get(i)
                if(previusElement==null || currentElement==null)
                    continue
                else
                    if(previusElement+1 != currentElement){
                        var specialLineHeight = matrix[j][i-1].lines.size
                        result.add(SpecialCordinatesAndMargin(j,i-1, specialHorizontalMargin(specialLineHeight, symbol)))
                        var temp=searchByAtomicNumber(previusElement+1)
                        temp.indexCol-=1
                        specialLineHeight = matrix[temp.indexRow][temp.indexCol].lines.size
                        result.add(SpecialCordinatesAndMargin(temp.indexRow,temp.indexCol, specialHorizontalMargin(specialLineHeight, symbol)))
                        temp=searchByAtomicNumber(currentElement-1)
                        specialLineHeight = matrix[temp.indexRow][temp.indexCol].lines.size
                        result.add(SpecialCordinatesAndMargin(temp.indexRow,temp.indexCol, specialHorizontalMargin(specialLineHeight, symbol)))
                        symbol= if( symbol.equals("\\") ) "/" else "\\"
                    }
                previusElement=currentElement
            }
        }
        return result
    }

    fun searchByAtomicNumber(atomic: Int): SpecialCordinates{
        for(i in 0 until elementMatrix.size){
            val index=elementMatrix.get(i).indexOf(atomic)
            if(index>=0)
                return SpecialCordinates(i, index)
        }
        return SpecialCordinates(-1,-1)
    }

    class SpecialCordinates(var indexRow: Int, var indexCol: Int)
    class SpecialCordinatesAndMargin(var indexRow: Int, var indexCol: Int, var marginRight: List<String>)

}