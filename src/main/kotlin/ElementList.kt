package org.example
class ElementList {
    private val elementsAsString="H 1 1 1\n" +
            "He 2 18 1\n" +
            "Li 3 1 2\n" +
            "Be 4 2 2\n" +
            "B 5 13 2\n" +
            "C 6 14 2\n" +
            "N 7 15 2\n" +
            "O 8 16 2\n" +
            "F 9 17 2\n" +
            "Ne 10 18 2\n" +
            "Na 11 1 3\n" +
            "Mg 12 2 3\n" +
            "Al 13 13 3\n" +
            "Si 14 14 3\n" +
            "P 15 15 3\n" +
            "S 16 16 3\n" +
            "Cl 17 17 3\n" +
            "Ar 18 18 3\n" +
            "K 19 1 4\n" +
            "Ca 20 2 4\n" +
            "Sc 21 3 4\n" +
            "Ti 22 4 4\n" +
            "V 23 5 4\n" +
            "Cr 24 6 4\n" +
            "Mn 25 7 4\n" +
            "Fe 26 8 4\n" +
            "Co 27 9 4\n" +
            "Ni 28 10 4\n" +
            "Cu 29 11 4\n" +
            "Zn 30 12 4\n" +
            "Ga 31 13 4\n" +
            "Ge 32 14 4\n" +
            "As 33 15 4\n" +
            "Se 34 16 4\n" +
            "Br 35 17 4\n" +
            "Kr 36 18 4\n" +
            "Rb 37 1 5\n" +
            "Sr 38 2 5\n" +
            "Y 39 3 5\n" +
            "Zr 40 4 5\n" +
            "Nb 41 5 5\n" +
            "Mo 42 6 5\n" +
            "Tc 43 7 5\n" +
            "Ru 44 8 5\n" +
            "Rh 45 9 5\n" +
            "Pd 46 10 5\n" +
            "Ag 47 11 5\n" +
            "Cd 48 12 5\n" +
            "In 49 13 5\n" +
            "Sn 50 14 5\n" +
            "Sb 51 15 5\n" +
            "Te 52 16 5\n" +
            "I 53 17 5\n" +
            "Xe 54 18 5\n" +
            "Cs 55 1 6\n" +
            "Ba 56 2 6\n" +
            "La 57 3 6\n" +
            "Hf 72 4 6\n" +
            "Ta 73 5 6\n" +
            "W 74 6 6\n" +
            "Re 75 7 6\n" +
            "Os 76 8 6\n" +
            "Ir 77 9 6\n" +
            "Pt 78 10 6\n" +
            "Au 79 11 6\n" +
            "Hg 80 12 6\n" +
            "Tl 81 13 6\n" +
            "Pb 82 14 6\n" +
            "Bi 83 15 6\n" +
            "Po 84 16 6\n" +
            "At 85 17 6\n" +
            "Rn 86 18 6\n" +
            "Fr 87 1 7\n" +
            "Ra 88 2 7\n" +
            "Ac 89 3 7\n" +
            "Rf 104 4 7\n" +
            "Db 105 5 7\n" +
            "Sg 106 6 7\n" +
            "Bh 107 7 7\n" +
            "Hs 108 8 7\n" +
            "Mt 109 9 7\n" +
            "Ds 110 10 7\n" +
            "Rg 111 11 7\n" +
            "Cn 112 12 7\n" +
            "Nh 113 13 7\n" +
            "Fl 114 14 7\n" +
            "Mc 115 15 7\n" +
            "Lv 116 16 7\n" +
            "Ts 117 17 7\n" +
            "Og 118 18 7\n" +
            "Ce 58 4 8\n" +
            "Pr 59 5 8\n" +
            "Nd 60 6 8\n" +
            "Pm 61 7 8\n" +
            "Sm 62 8 8\n" +
            "Eu 63 9 8\n" +
            "Gd 64 10 8\n" +
            "Tb 65 11 8\n" +
            "Dy 66 12 8\n" +
            "Ho 67 13 8\n" +
            "Er 68 14 8\n" +
            "Tm 69 15 8\n" +
            "Yb 70 16 8\n" +
            "Lu 71 17 8\n" +
            "Th 90 4 9\n" +
            "Pa 91 5 9\n" +
            "U 92 6 9\n" +
            "Np 93 7 9\n" +
            "Pu 94 8 9\n" +
            "Am 95 9 9\n" +
            "Cm 96 10 9\n" +
            "Bk 97 11 9\n" +
            "Cf 98 12 9\n" +
            "Es 99 13 9\n" +
            "Fm 100 14 9\n" +
            "Md 101 15 9\n" +
            "No 102 16 9\n" +
            "Lr 103 17 9";
    var elements : List<Element> = ArrayList<Element>();
    var rows = 0
    var cols = 0

    init{
        elements= stringToElementList(elementsAsString);
        rows=elements.maxWith(Comparator.comparingInt { it.period }).period
        cols=elements.maxWith(Comparator.comparingInt{it.group}).group
    }

    fun stringToElementList(text: String): List<Element>{
        var result= ArrayList<Element>()
        val textLines= text.lines()
        for (line in textLines){
            var stringSplitted = line.split(" ")
            result.add(Element(stringSplitted[0],stringSplitted[1].toInt(),stringSplitted[2].toInt(),stringSplitted[3].toInt()))
        }
        return result
    }
}