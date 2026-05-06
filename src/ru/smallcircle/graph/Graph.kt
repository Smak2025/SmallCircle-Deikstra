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

    /**
     * Поиск кратчайшего пути в графе
     * @param fromVertex    вершина, от которой ищется путь в графе
     * @param toVertex      вершина, до которой строится кратчайший путь
     * @return  Список вершин в порядке от [fromVertex] до [toVertex]
     * @throws  PathNotFoundException в случае если путь от [fromVertex] до
     *          [toVertex] отсутствует
     */
    fun findShortestPath(fromVertex: Vertex, toVertex: Vertex): List<Vertex> {
        val result = mutableListOf<Vertex>()
        fromVertex.minPathLength = 0.0
        var vFrom: Vertex?
        do {
            vFrom = getUnvisitedVertexWithMinPathLength()
            vFrom?.let {
                if (vFrom.minPathLength.isInfinite()) break
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
        toVertex.cameFrom?.let{
            result.add(toVertex.copy())
            while (result.last().cameFrom != null) {
                result.add(result.last().cameFrom!!.copy())
            }
        } ?: throw PathNotFoundException("Path not found")
        return result.reversed()
    }

    fun findShortestPathWithIntermediates(
        fromVertex: Vertex,
        toVertex: Vertex,
        intermediates: List<Vertex>,
    ): List<Vertex> {
        val inter = intermediates.toSet().filter { v -> v != fromVertex && v != toVertex }.toMutableList()
        var from = fromVertex
        var to: Vertex
        val result = mutableListOf<Vertex>()
        for (v in inter) {
            to = v
            result.addAll(findShortestPath(from, to))
            result.removeLastOrNull()
            inter.removeAll{ v -> result.any { v.id == it.id } }
            from = to
            reset()
        }
        result.addAll(findShortestPath(from, toVertex))
        return result
    }

    fun getResultLength(result: List<Vertex>) = result.foldIndexed(0.0) { index, acc, vertex ->
        acc + (if (index < result.size - 1) {
            val from = vertex
            val to = result[index + 1]
            val edge = edges.find { it.from.id == from.id && it.to.id == to.id }
            edge?.weight ?: 0.0
        }
        else 0.0)
    }


    fun reset(){
        vertices.forEach { v -> v.clear() }
    }
}