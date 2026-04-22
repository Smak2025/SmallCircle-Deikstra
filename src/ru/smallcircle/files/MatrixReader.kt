package ru.smallcircle.files

import java.io.FileReader

object MatrixReader {
    fun readFrom(file: String) = FileReader(file)
        .readAllLines().map{ row ->
            row.split(";").map{ elem ->
                elem.toDoubleOrNull() ?: 0.0
            }
        }
}