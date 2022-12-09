package org.example
class FrameDefaultImpl(vararg line: String): Frame {

    override val lines: List<String>

    init {
        lines= line.asList()
    }

    override fun atLeftOf(other: Frame): Frame {
        return this;
    }

    override fun onTopOf(other: Frame): Frame {
        return this;
    }

    override fun toString(): String {
        return "";
    }
}