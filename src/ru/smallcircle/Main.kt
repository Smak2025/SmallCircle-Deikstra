package ru.smallcircle

import ru.smallcircle.files.MatrixReader
import ru.smallcircle.graph.Graph

fun main() {
    val matrix = MatrixReader.readFrom("graph.csv")
    val g = Graph(matrix)
    g.findShortestPath(
        g.getVertexById(0)!!,
        g.getVertexById(4)!!,
    ).let{ vList ->
        println(vList.joinToString(separator = " -> ") { it.id.toString() })
        println("Длина пути: ${vList.last().minPathLength}")
    }
}