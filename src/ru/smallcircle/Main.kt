package ru.smallcircle

import ru.smallcircle.files.MatrixReader
import ru.smallcircle.graph.Graph
import ru.smallcircle.graph.PathNotFoundException

fun main() {
    val matrix = MatrixReader.readFrom("graph.csv")
    val g = Graph(matrix)
    try {
        g.findShortestPathWithIntermediates(
            g.getVertexById(0)!!,
            g.getVertexById(4)!!,
            listOf(g.getVertexById(2)!!, g.getVertexById(3)!!, g.getVertexById(1)!!)
        ).let { vList ->
            println(vList.joinToString(separator = " -> ") { it.id.toString() })
            println("Длина пути: ${g.getResultLength(vList)}")
        }
    } catch (e: PathNotFoundException) {
        println(e.message)
    }
}