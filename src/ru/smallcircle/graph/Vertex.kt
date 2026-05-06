package ru.smallcircle.graph

data class Vertex(
    val id: Int,
    var minPathLength: Double = Double.POSITIVE_INFINITY,
    var cameFrom: Vertex? = null,
    var visited: Boolean = false
){
    fun clear(){
        visited = false
        cameFrom = null
        minPathLength = Double.POSITIVE_INFINITY
    }
}