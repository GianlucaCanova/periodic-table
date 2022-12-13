package org.example

import hl

class Table(val matrix: Array<Array<Frame>>, val elementMatrix: Array<Array<Int?>>) {
    var lastSpecialChar = "/"
    val specialCases: List<Frame>
    init{
       val specialCasesCordinates = searchingSpecialCase()
       specialCases=ArrayList<Frame>()
       for(case in specialCasesCordinates){
           specialCases.add(matrix.get(case.indexRow).get(case.indexCol))
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

    fun horizontalMarginChoice(left: Frame, right: Frame): ArrayList<String> {
        var result = ArrayList<String>()
        for(case in specialCases){
            if(case===left)
                return specialHorizontalMargin(left.lines.size)
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

    fun specialHorizontalMargin(height: Int): ArrayList<String> {
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

    fun searchingSpecialCase(): List<MatrixCordinates> {
        var result = ArrayList<MatrixCordinates>()
        for(j in 0 until elementMatrix.size){
            var previusElement=elementMatrix.get(j).get(0)
            for(i in 1 until elementMatrix.get(j).size){
                val currentElement=elementMatrix.get(j).get(i)
                if(previusElement==null || currentElement==null)
                    continue
                else
                    if(previusElement+1 != currentElement){
                        result.add(MatrixCordinates(j,i-1))
                        var temp=searchByAtomicNumber(previusElement+1)
                        temp.indexCol-=1
                        println("Cordinates: ${temp}")
                        result.add(temp)
                        result.add(searchByAtomicNumber(currentElement-1))
                    }
                previusElement=currentElement
            }
        }
        return result
    }

    fun searchByAtomicNumber(atomic: Int): MatrixCordinates{
        for(i in 0 until elementMatrix.size){
            val index=elementMatrix.get(i).indexOf(atomic)
            if(index>=0)
                return MatrixCordinates(i, index)
        }
        return MatrixCordinates(-1,-1)
    }

    data class MatrixCordinates(var indexRow: Int, var indexCol: Int)
}