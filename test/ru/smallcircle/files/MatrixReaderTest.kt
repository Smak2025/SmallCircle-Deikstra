package ru.smallcircle.files

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MatrixReaderTest {
    @Test
    fun readFrom() {
        val actual = MatrixReader.readFrom("graph.csv")
        val expected = listOf(
            listOf( 0, 4, 10,  0,  0),
            listOf( 4, 0,  0,  3,  0),
            listOf(10, 0,  0,  2,  6),
            listOf( 0, 3,  2,  0, 11),
            listOf( 0, 0,  6, 11,  0),
        )
        assertEquals(expected, actual)
    }

}