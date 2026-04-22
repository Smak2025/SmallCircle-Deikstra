package ru.smallcircle.graph

class Graph(
    adjacencyMatrix: List<List<Double>>
) {
    val vertices = List(adjacencyMatrix.size) { i ->
        Vertex(i)
    }

    val edges = mutableListOf<Edge>().apply {
        adjacencyMatrix.forEachIndexed { rowIndex, fromVertex ->
            fromVertex.forEachIndexed { colIndex, weight ->
                if (weight > 1e-5)
                    add(Edge(
                        getVertexById(rowIndex)!!,
                        getVertexById(colIndex)!!,
                        weight
                    ))
            }
        }
    }

    fun getVertexById(id: Int) = vertices.find { it.id == id }

    fun getUnvisitedVertexWithMinPathLength(): Vertex? =
        vertices.filter { !it.visited }.minByOrNull { it.minPathLength }

    fun findShortestPath(fromVertex: Vertex, toVertex: Vertex): List<Vertex> {
        val result = mutableListOf<Vertex>()
        fromVertex.minPathLength = 0.0
        var vFrom: Vertex?
        do {
            vFrom = getUnvisitedVertexWithMinPathLength()
            vFrom?.let {
                edges.filter { it.from == vFrom }.forEach { edge ->
                    if (edge.to.minPathLength > vFrom.minPathLength + edge.weight) {
                        edge.to.apply {
                            minPathLength = vFrom.minPathLength + edge.weight
                            cameFrom = vFrom
                        }
                    }
                }
                vFrom.visited = true
            }
        } while (vFrom != null)
        result.add(toVertex)
        while (result.last().cameFrom != null) {
            result.add(result.last().cameFrom!!)
        }
        return result.reversed()
    }
}