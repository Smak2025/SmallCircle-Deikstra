package ru.smallcircle.graph

data class Vertex(
    val id: Int,
    var minPathLength: Double = Double.POSITIVE_INFINITY,
    var cameFrom: Vertex? = null,
    var visited: Boolean = false
)