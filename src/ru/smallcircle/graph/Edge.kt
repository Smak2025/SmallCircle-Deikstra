package ru.smallcircle.graph

data class Edge(
    val from: Vertex,
    val to: Vertex,
    val weight: Double,
)